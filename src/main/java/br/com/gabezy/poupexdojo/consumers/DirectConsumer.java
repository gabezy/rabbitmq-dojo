package br.com.gabezy.poupexdojo.consumers;

import br.com.gabezy.poupexdojo.dtos.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DirectConsumer {

    private static final Logger log = LoggerFactory.getLogger(DirectConsumer.class);

    @RabbitListener(queues = "${poupex-dojo.direct-routing-key}")
    public void receive(MessageDTO message) {
        log.info("Received Message: {}", message);
    }

}
