package org.airbnb.com.main.rmq;

import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sandeep.rana
 */
@Component
@Slf4j
public class Queues {

    private final String queue_name = "airbnb_customer_registration";

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private Listeners listeners;

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    public String getQueue_name() {
        return this.queue_name;
    }


    @PostConstruct
    public void createQueue() {
        QueueInformation qInfo =  rabbitAdmin.getQueueInfo(queue_name);
        if(qInfo == null || qInfo.getConsumerCount()<=0) {

            StopWatch sw = new StopWatch();

            sw.start("Start the queue declaration .. ");

            Map<String, Object> args = new HashMap<>();
            args.put("x-dead-letter-exchange", "DLX_MESSAGES_EXCHANGE");
            rabbitAdmin.declareQueue(new Queue(queue_name, true, false, false, args));

            sw.stop();

            sw.start("Start to add the queue in container with listener");

            SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
            container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
            container.setConnectionFactory(cachingConnectionFactory);
            container.setMessageListener(listeners.receiveRegist());
            container.setConcurrentConsumers(1);
            container.setQueueNames(queue_name);
            container.start();

            sw.stop();
            log.info("After queue added successfully {}", sw.prettyPrint());
        } else {
            log.info("{} already exits ", queue_name);
        }
    }

}
