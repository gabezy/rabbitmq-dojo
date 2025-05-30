package br.com.gabezy.poupexdojo.config.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("poupex-dojo")
public class PoupexDojoProperties {

    private String fanoutExchange;
    private String topicExchange;
    private String deadLetterFanoutExchange;
    private Queue queue;

    public static class Queue {
        private String directQueue;
        private String fanoutQueue;
        private String fanoutQueue2;
        private String deadLetterFanoutQueue;
        private String arquivosTopicQueue;
        private String assuntosTopicQueue;
        private String pagamentosTopicQueue;

        public String getDirectQueue() {
            return directQueue;
        }

        public void setDirectQueue(String directQueue) {
            this.directQueue = directQueue;
        }

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

        public String getDeadLetterFanoutQueue() {
            return deadLetterFanoutQueue;
        }

        public void setDeadLetterFanoutQueue(String deadLetterFanoutQueue) {
            this.deadLetterFanoutQueue = deadLetterFanoutQueue;
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

    public String getDeadLetterFanoutExchange() {
        return deadLetterFanoutExchange;
    }

    public void setDeadLetterFanoutExchange(String deadLetterFanoutExchange) {
        this.deadLetterFanoutExchange = deadLetterFanoutExchange;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }
}