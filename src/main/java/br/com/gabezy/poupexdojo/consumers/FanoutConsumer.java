package br.com.gabezy.poupexdojo.consumers;

import br.com.gabezy.poupexdojo.config.properties.PoupexDojoProperties;
import br.com.gabezy.poupexdojo.dtos.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutConsumer {

    private static final Logger log = LoggerFactory.getLogger(FanoutConsumer.class);

    private final PoupexDojoProperties properties;

    public FanoutConsumer(PoupexDojoProperties properties) {
        this.properties = properties;
    }

    @RabbitListener(queues = "${poupex-dojo.queue.fanout-queue}")
    public void receive(MessageDTO message) {
        log.info("Mensagem recebida na fila {}: {}", properties.getQueue().getFanoutQueue(), message);
    }

    @RabbitListener(queues = "${poupex-dojo.queue.fanout-queue-2}")
    public void receive2(MessageDTO message) {
        log.info("Mensagem recebida na fila {}: {}", properties.getQueue().getFanoutQueue2(), message);
    }

}
