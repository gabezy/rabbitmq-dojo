package br.com.gabezy.poupexdojo.testscontainers;

import br.com.gabezy.poupexdojo.consumers.DirectConsumer;
import br.com.gabezy.poupexdojo.consumers.FanoutConsumer;
import br.com.gabezy.poupexdojo.consumers.TopicConsumer;
import br.com.gabezy.poupexdojo.dtos.MessageDTO;
import br.com.gabezy.poupexdojo.services.DirectService;
import br.com.gabezy.poupexdojo.services.FanoutService;
import br.com.gabezy.poupexdojo.services.TopicService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;

class ProducersRabbitMqTest extends BaseRabbitMqTest {

    // consumer desabilitado
    @MockitoBean
    private DirectConsumer directConsumer;

    // consumer desabilitado
    @MockitoBean
    private FanoutConsumer fanoutConsumer;

    // consumer desabilitado
    @MockitoBean
    private TopicConsumer topicConsumer;

    @Autowired
    private DirectService directService;

    @Autowired
    private FanoutService fanoutService;

    @Autowired
    private TopicService topicService;

    private static final String MENSAGEM_EXAMPLO = "uma mensagem";

    @Test
    void deveEnviarMessagemParaFilaDirect() {
        var mensagemParaEnviar = new MessageDTO(MENSAGEM_EXAMPLO);

        directService.sendMessage(mensagemParaEnviar);

        MessageDTO mensagemConsumida = rabbitTemplate.receiveAndConvert(poupexDojoProperties.getQueue().getDirectQueue(),
                2_000, ParameterizedTypeReference.forType(MessageDTO.class));

        assertThat(mensagemConsumida).isNotNull();
        assertThat(mensagemConsumida.message()).isEqualTo(MENSAGEM_EXAMPLO);
    }

    @Test
    void deveEnviarMessagemParaFilasFanout() {
        var mensagemParaEnviar = new MessageDTO(MENSAGEM_EXAMPLO);

        fanoutService.sendMessage(mensagemParaEnviar);

        String filaFanout = poupexDojoProperties.getQueue().getFanoutQueue();
        String filaFanout2 = poupexDojoProperties.getQueue().getFanoutQueue2();

        MessageDTO mensagemConsumidaFila1 = rabbitTemplate.receiveAndConvert(filaFanout, 2_000,
                ParameterizedTypeReference.forType(MessageDTO.class));


        MessageDTO mensagemConsumidaFila2 = rabbitTemplate.receiveAndConvert(filaFanout2, 2_000,
                ParameterizedTypeReference.forType(MessageDTO.class));

        assertThat(mensagemConsumidaFila1).isNotNull();
        assertThat(mensagemConsumidaFila2).isNotNull();
        assertThat(mensagemConsumidaFila1.message()).isEqualTo(MENSAGEM_EXAMPLO);
        assertThat(mensagemConsumidaFila2.message()).isEqualTo(MENSAGEM_EXAMPLO);
    }

    @Test
    void deveEnviarMensagemParaFilasArquivoAndPagamento() {
        var mensagem = "essa mensagem é sobre arquivos de pagamentos de fornecedores";

        var routingKey = "arquivos.pagamentos.fornecedores";

        var mensagemParaEnviar = new MessageDTO(mensagem);

        topicService.sendMessage(routingKey, mensagemParaEnviar);

        String filaArquivos = poupexDojoProperties.getQueue().getArquivosTopicQueue();
        String filaPagamentos = poupexDojoProperties.getQueue().getPagamentosTopicQueue();
        String filaAssuntos = poupexDojoProperties.getQueue().getAssuntosTopicQueue();

        MessageDTO mensagemConsumidaFilaArquivos = rabbitTemplate.receiveAndConvert(filaArquivos, 2_000,
                ParameterizedTypeReference.forType(MessageDTO.class));

        MessageDTO mensagemConsumidaFilaPagamentos = rabbitTemplate.receiveAndConvert(filaPagamentos, 2_000,
                ParameterizedTypeReference.forType(MessageDTO.class));

        Object mensagemConsumidaFilaAssuntos = rabbitTemplate.receiveAndConvert(filaAssuntos, 2_000);

        assertThat(mensagemConsumidaFilaArquivos).isNotNull();
        assertThat(mensagemConsumidaFilaPagamentos).isNotNull();

        assertThat(mensagemConsumidaFilaAssuntos).isNull();

        assertThat(mensagemConsumidaFilaArquivos.message()).isEqualTo(mensagem);
        assertThat(mensagemConsumidaFilaPagamentos.message()).isEqualTo(mensagem);
    }

    @Test
    void deveEnviarMensagemParaFilasAssuntos() {
        var mensagem = "essa mensagem é assuntos";

        var routingKey = "assuntos";

        var mensagemParaEnviar = new MessageDTO(mensagem);

        topicService.sendMessage(routingKey, mensagemParaEnviar);

        String filaAssuntos = poupexDojoProperties.getQueue().getAssuntosTopicQueue();
        String filaArquivos = poupexDojoProperties.getQueue().getArquivosTopicQueue();
        String filaPagamentos = poupexDojoProperties.getQueue().getPagamentosTopicQueue();

        MessageDTO mensagemConsumidaFilaAssuntos = rabbitTemplate.receiveAndConvert(filaAssuntos, 2_000,
                ParameterizedTypeReference.forType(MessageDTO.class));

        MessageDTO mensagemConsumidaFilaArquivos = rabbitTemplate.receiveAndConvert(filaArquivos, 2_000,
                ParameterizedTypeReference.forType(MessageDTO.class));

        MessageDTO mensagemConsumidaFilaPagamentos = rabbitTemplate.receiveAndConvert(filaPagamentos, 2_000,
                ParameterizedTypeReference.forType(MessageDTO.class));

        assertThat(mensagemConsumidaFilaAssuntos).isNotNull();
        assertThat(mensagemConsumidaFilaAssuntos.message()).isEqualTo(mensagem);

        assertThat(mensagemConsumidaFilaArquivos).isNull();
        assertThat(mensagemConsumidaFilaPagamentos).isNull();
    }

}
