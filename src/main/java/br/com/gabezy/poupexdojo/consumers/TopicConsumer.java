package br.com.gabezy.poupexdojo.consumers;

import br.com.gabezy.poupexdojo.config.properties.PoupexDojoProperties;
import br.com.gabezy.poupexdojo.dtos.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicConsumer {

    private static final Logger log = LoggerFactory.getLogger(TopicConsumer.class);

    private final PoupexDojoProperties properties;

    public TopicConsumer(PoupexDojoProperties properties) {
        this.properties = properties;
    }

    @RabbitListener(queues = "${poupex-dojo.queue.arquivos-topic-queue}")
    public void receiveArquivosMessage(MessageDTO messageDTO) {
        log.info("Mensagem recebida na fila {} : {}", properties.getQueue().getArquivosTopicQueue(), messageDTO);
    }


    @RabbitListener(queues = "${poupex-dojo.queue.assuntos-topic-queue}")
    public void receiveAssuntosMessage(MessageDTO messageDTO) {
        log.info("Mensagem recebida na fila {}: {}", properties.getQueue().getAssuntosTopicQueue(), messageDTO);
    }

    @RabbitListener(queues = "${poupex-dojo.queue.pagamentos-topic-queue}")
    public void receivePagamentosMessage(MessageDTO messageDTO) {
        log.info("Mensagem recebida na fila {}: {}", properties.getQueue().getPagamentosTopicQueue(), messageDTO);
    }

}
