package br.com.gabezy.poupexdojo.consumers;

import br.com.gabezy.poupexdojo.config.properties.PoupexDojoProperties;
import br.com.gabezy.poupexdojo.dtos.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DirectConsumer {

    private static final Logger log = LoggerFactory.getLogger(DirectConsumer.class);

    private final PoupexDojoProperties properties;

    public DirectConsumer(PoupexDojoProperties properties) {
        this.properties = properties;
    }

    @RabbitListener(queues = "${poupex-dojo.queue.direct-queue}")
    public void receive(MessageDTO message) {
        log.info("Mensagem recebida na fila {}: {}", properties.getQueue().getDirectQueue(), message);
    }

}
