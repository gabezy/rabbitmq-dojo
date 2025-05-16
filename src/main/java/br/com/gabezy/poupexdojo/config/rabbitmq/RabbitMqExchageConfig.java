package br.com.gabezy.poupexdojo.config.rabbitmq;

import br.com.gabezy.poupexdojo.config.properties.PoupexDojoProperties;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqExchageConfig {

    private final PoupexDojoProperties poupexDojoProperties;

    public RabbitMqExchageConfig(PoupexDojoProperties poupexDojoProperties) {
        this.poupexDojoProperties = poupexDojoProperties;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initializeAdmin(RabbitAdmin rabbitAdmin) {
        // This will initialize the RabbitMQ admin and create the queues if they do not exist
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public Queue directQueue() {
        return QueueBuilder.durable(poupexDojoProperties.getQueue().getDirectQueue())
                .build();
    }

    @Bean
    public FanoutExchange deadLetterFanoutExchange() {
        return ExchangeBuilder.fanoutExchange(poupexDojoProperties.getDeadLetterFanoutExchange())
                .build();
    }

    @Bean
    public Queue deadLetterFanoutQueue() {
        return QueueBuilder.durable(poupexDojoProperties.getQueue().getDeadLetterFanoutQueue()).build();
    }

    @Bean
    public Binding deadLetterFanoutBinding() {
        return BindingBuilder.bind(deadLetterFanoutQueue()).to(deadLetterFanoutExchange());
    }

    @Bean
    public Queue fanoutQueue() {
        int tenSecondsInMiliseconds = 1000 * 10;
        return QueueBuilder.durable(poupexDojoProperties.getQueue().getFanoutQueue())
                .deadLetterExchange(poupexDojoProperties.getDeadLetterFanoutExchange())
                .ttl(tenSecondsInMiliseconds)
                .build();
    }

    @Bean
    public Binding bindingFanoutQueue() {
        return BindingBuilder.bind(fanoutQueue())
                .to(new FanoutExchange(poupexDojoProperties.getFanoutExchange()));
    }

    @Bean
    public Queue fanoutQueue2() {
        return QueueBuilder.durable(poupexDojoProperties.getQueue().getFanoutQueue2()).build();
    }

    @Bean
    public Binding bindingFanoutQueue2() {
        return BindingBuilder.bind(fanoutQueue2())
                .to(new FanoutExchange(poupexDojoProperties.getFanoutExchange()));
    }

    @Bean
    public Queue arquivosTopicQueue() {
        return QueueBuilder.durable(poupexDojoProperties.getQueue().getArquivosTopicQueue()).build();
    }

    @Bean
    public Binding bindingArquivosTopicQueue() {
        return BindingBuilder.bind(arquivosTopicQueue())
                .to(new TopicExchange(poupexDojoProperties.getTopicExchange()))
                .with("arquivos.#");
    }

    @Bean
    public Queue assuntosTopicQueue() {
        return QueueBuilder.durable(poupexDojoProperties.getQueue().getAssuntosTopicQueue()).build();
    }

    @Bean
    public Binding bindingAssuntosTopicQueue() {
        return BindingBuilder.bind(assuntosTopicQueue())
                .to(new TopicExchange(poupexDojoProperties.getTopicExchange()))
                .with("assuntos.#");
    }

    @Bean
    public Queue pagamentosTopicQueue() {
        return QueueBuilder.durable(poupexDojoProperties.getQueue().getPagamentosTopicQueue()).build();
    }

    @Bean
    public Binding bindingPagamentosTopicQueue() {
        return BindingBuilder.bind(pagamentosTopicQueue())
                .to(new TopicExchange(poupexDojoProperties.getTopicExchange()))
                .with("#.pagamentos.#");
    }

}
