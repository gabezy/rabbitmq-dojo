package br.com.gabezy.poupexdojo.config.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("poupex-dojo")
public class PoupexDojoProperties {

    private String directRoutingKey;
    private String fanoutExchange;
    private String topicExchange;

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
}