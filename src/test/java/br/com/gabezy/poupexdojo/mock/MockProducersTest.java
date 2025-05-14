package br.com.gabezy.poupexdojo.mock;

import br.com.gabezy.poupexdojo.config.properties.PoupexDojoProperties;
import br.com.gabezy.poupexdojo.dtos.MessageDTO;
import br.com.gabezy.poupexdojo.services.DirectService;
import br.com.gabezy.poupexdojo.services.FanoutService;
import br.com.gabezy.poupexdojo.services.TopicService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


// TODO: Ajustar
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MockProducersTest {

    @MockitoBean
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PoupexDojoProperties poupexDojoProperties;

    @Autowired
    private DirectService directService;

    @Autowired
    private FanoutService fanoutService;

    @Autowired
    private TopicService topicService;

    @Test
    void deveEnviarMensagemParaDirectExchange() {
        var message = new MessageDTO("Uma mensagem");

        directService.sendMessage(message);

        verify(rabbitTemplate, times(1)).convertAndSend("", poupexDojoProperties.getDirectRoutingKey(), message);
    }

    @Test
    void deveEnviarMensagemParaFanoutExchange() {
        var message = new MessageDTO("Uma mensagem");

        fanoutService.sendMessage(message);

        verify(rabbitTemplate, times(1)).convertAndSend(poupexDojoProperties.getFanoutExchange(), "",  message);
    }


    @Test
    void deveEnviarMensagemParaTopicExchange() {
        var message = new MessageDTO("Uma mensagem");

        var bindingKey = "bindingKey";

        topicService.sendMessage(bindingKey, message);

        verify(rabbitTemplate, times(1)).convertAndSend(poupexDojoProperties.getTopicExchange(), bindingKey,  message);
    }


}
