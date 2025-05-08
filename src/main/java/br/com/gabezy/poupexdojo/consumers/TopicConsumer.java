package br.com.gabezy.poupexdojo.consumers;

import br.com.gabezy.poupexdojo.dtos.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicConsumer {

    private static final Logger log = LoggerFactory.getLogger(TopicConsumer.class);

    @RabbitListener(queues = "${poupex-dojo.queue.arquivos-topic-queue}")
    public void receiveArquivosMessage(MessageDTO messageDTO) {
        log.info("Mensagem de arquivos recebido: {}", messageDTO);
    }


    @RabbitListener(queues = "${poupex-dojo.queue.assuntos-topic-queue}")
    public void receiveAssuntosMessage(MessageDTO messageDTO) {
        log.info("Mensagem de assuntos recebido: {}", messageDTO);
    }

    @RabbitListener(queues = "${poupex-dojo.queue.pagamentos-topic-queue}")
    public void receivePagamentosMessage(MessageDTO messageDTO) {
        log.info("Mensagem de pagamentos recebido: {}", messageDTO);
    }

}
