package br.com.gabezy.poupexdojo.mock;

import br.com.gabezy.poupexdojo.config.properties.PoupexDojoProperties;
import br.com.gabezy.poupexdojo.dtos.MessageDTO;
import br.com.gabezy.poupexdojo.services.DirectService;
import br.com.gabezy.poupexdojo.services.FanoutService;
import br.com.gabezy.poupexdojo.services.TopicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MockProducersTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private PoupexDojoProperties poupexDojoProperties;

    @InjectMocks
    private DirectService directService;

    @InjectMocks
    private FanoutService fanoutService;

    @InjectMocks
    private TopicService topicService;

    @Test
    void deveEnviarMensagemParaDirectExchange() {
        var message = new MessageDTO("Uma mensagem");

        when(poupexDojoProperties.getDirectRoutingKey()).thenReturn("direct-queue");

        directService.sendMessage(message);

        verify(poupexDojoProperties, times(1)).getDirectRoutingKey();
        verify(rabbitTemplate, times(1)).convertAndSend("", "direct-queue", message);
    }

    @Test
    void deveEnviarMensagemParaFanoutExchange() {
        var message = new MessageDTO("Uma mensagem");

        when(poupexDojoProperties.getFanoutExchange()).thenReturn("amq.fanout");

        fanoutService.sendMessage(message);

        verify(poupexDojoProperties, times(1)).getFanoutExchange();
        verify(rabbitTemplate, times(1)).convertAndSend("amq.fanout", "",  message);
    }


    @Test
    void deveEnviarMensagemParaTopicExchange() {
        var message = new MessageDTO("Uma mensagem");

        var bindingKey = "bindingKey";

        when(poupexDojoProperties.getTopicExchange()).thenReturn("amq.topic");

        topicService.sendMessage(bindingKey, message);

        verify(poupexDojoProperties, times(1)).getTopicExchange();
        verify(rabbitTemplate, times(1)).convertAndSend("amq.topic", bindingKey,  message);
    }


}
