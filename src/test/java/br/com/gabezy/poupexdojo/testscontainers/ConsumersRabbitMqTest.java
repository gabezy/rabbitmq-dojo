package br.com.gabezy.poupexdojo.testscontainers;

import br.com.gabezy.poupexdojo.consumers.DirectConsumer;
import br.com.gabezy.poupexdojo.consumers.FanoutConsumer;
import br.com.gabezy.poupexdojo.consumers.TopicConsumer;
import br.com.gabezy.poupexdojo.dtos.MessageDTO;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.shaded.org.awaitility.core.ThrowingRunnable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class ConsumersRabbitMqTest extends BaseRabbitMqTest {

    @MockitoSpyBean
    private DirectConsumer directConsumer;

    @MockitoSpyBean
    private FanoutConsumer fanoutConsumer;

    @MockitoSpyBean
    private TopicConsumer topicConsumer;

    @Test
    void deveConsumirMensagemEnviadaParaDirectQueue() {
        var mensagem = "essa é uma mensagem para direct queue";

        var mensagemDTO = new MessageDTO(mensagem);

        rabbitTemplate.convertAndSend("", poupexDojoProperties.getQueue().getDirectQueue(), mensagemDTO);

        Awaitility.await()
                .untilAsserted(() ->
                        verify(directConsumer, times(1)).receive(argThat(message -> message.message().equals(mensagem))));
    }

    @Test
    void deveConsumirMensagemEnviadaParaFanoutQueues() {
        var mensagem = "essa é uma mensagem para fanout queues";

        var mensagemDTO = new MessageDTO(mensagem);

        rabbitTemplate.convertAndSend(poupexDojoProperties.getFanoutExchange(), "", mensagemDTO);

        ThrowingRunnable verificaSeAsMensagensForamConsumidas = () -> {
            verify(fanoutConsumer, times(1)).receive(argThat(message -> message.message().equals(mensagem)));
            verify(fanoutConsumer, times(1)).receive2(argThat(message -> message.message().equals(mensagem)));
        };

        Awaitility.await()
                .untilAsserted(verificaSeAsMensagensForamConsumidas);

    }

    @Test
    void deveConsumirMensagemEnviadaParaArquivosEPagamentosTopicQueue() {
        var mensagem = "essa mensagem é sobre arquivos de pagamentos de fornecedores";

        var routingKey = "arquivos.pagamentos.fornecedores";

        var mensagemParaEnviar = new MessageDTO(mensagem);

        rabbitTemplate.convertAndSend(poupexDojoProperties.getTopicExchange(), routingKey, mensagemParaEnviar);

        ThrowingRunnable verificaSeMensagensForamConsumidas = () -> {
            verify(topicConsumer, times(1)).receiveArquivosMessage(argThat(message -> message.message().equals(mensagem)));
            verify(topicConsumer, times(1)).receivePagamentosMessage(argThat(message -> message.message().equals(mensagem)));
            verify(topicConsumer, never()).receiveAssuntosMessage(any());
        };

        Awaitility.await()
                .untilAsserted(verificaSeMensagensForamConsumidas);
    }

    @Test
    void deveConsumirMensagemEnviadaParaAssuntosTopicQueue() {
        var mensagem = "essa mensagem é assuntos";

        var routingKey = "assuntos";

        var mensagemParaEnviar = new MessageDTO(mensagem);

        rabbitTemplate.convertAndSend(poupexDojoProperties.getTopicExchange(), routingKey, mensagemParaEnviar);

        ThrowingRunnable verificaSeMensagensForamConsumidas = () -> {
            verify(topicConsumer, times(1)).receiveAssuntosMessage(argThat(message -> message.message().equals(mensagem)));
            verify(topicConsumer, never()).receiveArquivosMessage(any());
            verify(topicConsumer, never()).receivePagamentosMessage(any());
        };

        Awaitility.await()
                .untilAsserted(verificaSeMensagensForamConsumidas);
    }


}
