package br.com.gabezy.poupexdojo.services;

import br.com.gabezy.poupexdojo.config.properties.PoupexDojoProperties;
import br.com.gabezy.poupexdojo.dtos.MessageDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class TopicService {

    private final RabbitTemplate rabbitTemplate;
    private final PoupexDojoProperties properties;

    public TopicService(RabbitTemplate rabbitTemplate, PoupexDojoProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    public void sendMessage(String bindingKey, @RequestBody MessageDTO message) {
        rabbitTemplate.convertAndSend(properties.getTopicExchange(), bindingKey,  message);
    }

}
