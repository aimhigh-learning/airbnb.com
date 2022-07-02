package org.airbnb.com.main.admin;

import org.airbnb.com.main.customer.CustomerEntity;
import org.airbnb.com.main.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * {@link org.springframework.stereotype.Controller} to manage all request for /admin
 * @author sandeep.rana
 */
@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(path = "/welcome")
    public String welcome() {
        return "welcome.html";
    }

    @GetMapping(path = "/customer/_all")
    @ResponseBody
    public Page<CustomerEntity> customerList(@RequestParam(required = false, defaultValue = "") String s) {
        return customerService.getCustomers(s);
    }
}
