package br.com.gabezy.poupexdojo;

import br.com.gabezy.poupexdojo.config.properties.PoupexDojoProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(PoupexDojoProperties.class)
public class PoupexDojoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PoupexDojoApplication.class, args);
    }

}
