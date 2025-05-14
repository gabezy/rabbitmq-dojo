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
        return QueueBuilder.durable(poupexDojoProperties.getDirectRoutingKey())
                .build();
    }

    @Bean
    public Queue fanoutQueue() {
        return QueueBuilder.durable("fanout-queue")
                .build();
    }

    @Bean
    public Binding bindingFanoutQueue() {
        return BindingBuilder.bind(fanoutQueue())
                .to(new FanoutExchange("amq.fanout"));
    }

    @Bean
    public Queue fanoutQueue2() {
        return QueueBuilder.durable("fanout-queue-2").build();
    }

    @Bean
    public Binding bindingFanoutQueue2() {
        return BindingBuilder.bind(fanoutQueue2())
                .to(new FanoutExchange("amq.fanout"));
    }

    @Bean
    public Queue arquivosTopicQueue() {
        return QueueBuilder.durable("arquivos-topic-queue").build();
    }

    @Bean
    public Binding bindingArquivosTopicQueue() {
        return BindingBuilder.bind(arquivosTopicQueue())
                .to(new TopicExchange("amq.topic"))
                .with("arquivos.#");
    }

    @Bean
    public Queue assuntosTopicQueue() {
        return QueueBuilder.durable("assuntos-topic-queue").build();
    }

    @Bean
    public Binding bindingAssuntosTopicQueue() {
        return BindingBuilder.bind(assuntosTopicQueue())
                .to(new TopicExchange("amq.topic"))
                .with("assuntos.#");
    }

    @Bean
    public Queue pagamentosTopicQueue() {
        return QueueBuilder.durable("pagamentos-topic-queue").build();
    }

    @Bean
    public Binding bindingPagamentosTopicQueue() {
        return BindingBuilder.bind(pagamentosTopicQueue())
                .to(new TopicExchange("amq.topic"))
                .with("#.pagamentos.#");
    }

}
