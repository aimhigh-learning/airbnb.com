package org.airbnb.com.main.rmq;

import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author sandeep.rana
 */
@Configuration
@Slf4j
public class Config {


    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Bean
    public ConnectionFactory factory() {
        log.info(" Initializing rabbitmq connection Factory .. ");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setHost(host);
        factory.setPort(port);
        return factory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory(){
        log.info(" Initializing RabbitMq CachingConnectionFactory .. ");
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(factory());
        return cachingConnectionFactory;

    }

    @Bean("rabbitAdmin")
    public RabbitAdmin amqpAdmin(){
        return new RabbitAdmin(cachingConnectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        return new RabbitTemplate(cachingConnectionFactory());
    }
}
