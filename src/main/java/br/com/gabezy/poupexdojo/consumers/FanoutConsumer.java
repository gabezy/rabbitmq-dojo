package br.com.gabezy.poupexdojo.consumers;

import br.com.gabezy.poupexdojo.dtos.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutConsumer {

    private static final Logger log = LoggerFactory.getLogger(FanoutConsumer.class);

    @RabbitListener(queues = "${poupex-dojo.queue.fanout-queue}")
    public void receive(MessageDTO message) {
        log.info("Mensagem recebida {}", message);
    }

    @RabbitListener(queues = "${poupex-dojo.queue.fanout-queue-2}")
    public void receive2(MessageDTO message) {
        log.info("Mensagem recebida {}", message);
    }

}
