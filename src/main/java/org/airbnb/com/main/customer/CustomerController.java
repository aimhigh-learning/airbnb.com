package org.airbnb.com.main.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.airbnb.com.main.rmq.Queues;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @GetMapping(path = "/register-me")
    public String customerRegPage() {
        return "register-me";
    }

    @GetMapping(path = "/check-status")
    public String customerStatusPage() {
        return "customer-details";
    }


    @PostMapping(path = "/reg/_new")
    @ResponseBody
    public ResponseEntity<ResponseDto> regCustomer(@RequestBody CustomerEntity customerEntity, TimeZone timezone) throws JsonProcessingException {
        customerEntity.setTimeZone(timezone.getID());
        customerEntity.setCustomerId(UUID.randomUUID().toString());
        byte[] sendBody = new ObjectMapper().writeValueAsBytes(customerEntity);
        rabbitTemplate.convertAndSend(queues.getQueue_name(), sendBody);
        return ResponseEntity.ok(new ResponseDto().setMessage("Accepted").setStatus(HttpStatus.CREATED).setData(customerEntity.getCustomerId()));
    }

    @GetMapping(path = "get")
    @ResponseBody
    public Optional<CustomerEntity> getCustomerInfo(@RequestParam String _id) {
        return customerService.getDetails(_id);
    }
}
