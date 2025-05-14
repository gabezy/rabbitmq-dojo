package br.com.gabezy.poupexdojo.config.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("poupex-dojo")
public class PoupexDojoProperties {

    private String directRoutingKey;
    private String fanoutExchange;
    private String topicExchange;
    private Queue queue;

    public static class Queue {
        private String fanoutQueue;
        private String fanoutQueue2;
        private String arquivosTopicQueue;
        private String assuntosTopicQueue;
        private String pagamentosTopicQueue;

        public String getFanoutQueue() {
            return fanoutQueue;
        }

        public void setFanoutQueue(String fanoutQueue) {
            this.fanoutQueue = fanoutQueue;
        }

        public String getFanoutQueue2() {
            return fanoutQueue2;
        }

        public void setFanoutQueue2(String fanoutQueue2) {
            this.fanoutQueue2 = fanoutQueue2;
        }

        public String getArquivosTopicQueue() {
            return arquivosTopicQueue;
        }

        public void setArquivosTopicQueue(String arquivosTopicQueue) {
            this.arquivosTopicQueue = arquivosTopicQueue;
        }

        public String getAssuntosTopicQueue() {
            return assuntosTopicQueue;
        }

        public void setAssuntosTopicQueue(String assuntosTopicQueue) {
            this.assuntosTopicQueue = assuntosTopicQueue;
        }

        public String getPagamentosTopicQueue() {
            return pagamentosTopicQueue;
        }

        public void setPagamentosTopicQueue(String pagamentosTopicQueue) {
            this.pagamentosTopicQueue = pagamentosTopicQueue;
        }
    }

    public String getDirectRoutingKey() {
        return directRoutingKey;
    }

    public void setDirectRoutingKey(String directRoutingKey) {
        this.directRoutingKey = directRoutingKey;
    }

    public String getFanoutExchange() {
        return fanoutExchange;
    }

    public void setFanoutExchange(String fanoutExchange) {
        this.fanoutExchange = fanoutExchange;
    }

    public String getTopicExchange() {
        return topicExchange;
    }

    public void setTopicExchange(String topicExchange) {
        this.topicExchange = topicExchange;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }
}