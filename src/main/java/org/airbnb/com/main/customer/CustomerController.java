package org.airbnb.com.main.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.airbnb.com.main.rmq.Queues;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;

/**
 * @author sandeep.rana
 */
@Controller
@RequestMapping(path = "/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queues queues;

    @PostMapping(path = "/reg/_new")
    @ResponseBody
    public String regCustomer(@RequestBody CustomerEntity customerEntity, TimeZone timezone) throws JsonProcessingException {
        customerEntity.setTimeZone(timezone.getID());
        customerEntity.setCustomerId(UUID.randomUUID().toString());
        byte[] sendBody = new ObjectMapper().writeValueAsBytes(customerEntity);
        rabbitTemplate.convertAndSend(queues.getQueue_name(), sendBody);
        return customerEntity.getCustomerId();
    }

    @GetMapping(path = "get")
    @ResponseBody
    public Optional<CustomerEntity> getCustomerInfo(@RequestParam String _id) {
        return customerService.getDetails(_id);
    }
}
