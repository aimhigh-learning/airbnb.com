package org.airbnb.com.main.rmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.airbnb.com.main.customer.CustomerEntity;
import org.airbnb.com.main.customer.CustomerService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author sandeep.rana
 */
@Component
@RabbitListener
@Slf4j
public class Listeners {


    @Autowired
    private CustomerService customerService;

    @Bean
    public ChannelAwareMessageListener receiveRegist() {
        return new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                log.info("In listener of registration");
                CustomerEntity ce = new ObjectMapper().readValue(message.getBody(), CustomerEntity.class);
                log.info("Customer data  {}", ce.toString());
                try{
                    customerService.addCustomer(ce);
                    log.info("{} saved successfully", ce.getCustomerId());
                }finally {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                }

            }
        };
    }
}
