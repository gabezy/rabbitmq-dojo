package br.com.gabezy.poupexdojo.services;

import br.com.gabezy.poupexdojo.config.properties.PoupexDojoProperties;
import br.com.gabezy.poupexdojo.dtos.MessageDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class DirectService {

    private final RabbitTemplate rabbitTemplate;
    private final PoupexDojoProperties poupexDojoProperties;

    public DirectService(RabbitTemplate rabbitTemplate, PoupexDojoProperties poupexDojoProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.poupexDojoProperties = poupexDojoProperties;
    }

    public void sendMessage(MessageDTO message) {
        rabbitTemplate.convertAndSend("", poupexDojoProperties.getDirectRoutingKey(), message);
    }

}
