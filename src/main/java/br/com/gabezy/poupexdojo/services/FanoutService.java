package br.com.gabezy.poupexdojo.services;

import br.com.gabezy.poupexdojo.config.properties.PoupexDojoProperties;
import br.com.gabezy.poupexdojo.dtos.MessageDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class FanoutService {

    private final RabbitTemplate rabbitTemplate;
    private final PoupexDojoProperties properties;

    public FanoutService(RabbitTemplate rabbitTemplate, PoupexDojoProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    public void sendMessage(MessageDTO message) {
        rabbitTemplate.convertAndSend(properties.getFanoutExchange(), "", message);
    }

}
