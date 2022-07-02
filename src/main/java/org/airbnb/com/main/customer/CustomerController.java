package org.airbnb.com.main.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.TimeZone;

/**
 * @author sandeep.rana
 */
@Controller
@RequestMapping(path = "/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(path = "/reg/_new")
    @ResponseBody
    public String regCustomer(@RequestBody CustomerEntity customerEntity, TimeZone timezone) {
        customerEntity.setTimeZone(timezone.getID());
        return customerService.addCustomer(customerEntity);
    }

    @GetMapping(path = "get")
    @ResponseBody
    public Optional<CustomerEntity> getCustomerInfo(@RequestParam String _id) {
        return customerService.getDetails(_id);
    }
}
