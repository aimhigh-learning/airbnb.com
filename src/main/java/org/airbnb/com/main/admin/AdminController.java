package org.airbnb.com.main.admin;

import org.airbnb.com.main.customer.CustomerEntity;
import org.airbnb.com.main.customer.CustomerService;
import org.airbnb.com.main.customer.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

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

    @GetMapping(path = "/call/customer")
    public ResponseEntity<ResponseDto> callCustomer(@RequestParam String _id) {
        Optional<CustomerEntity> ce = customerService.getDetails(_id);
        boolean ckh = !ce.isEmpty() && ce.get().getCanWeCall();
        if(!ce.isEmpty() && !CustomerEntity.Status.confirmed.name().equalsIgnoreCase(ce.get().getStatus().name())) {
            customerService.updateStatus(_id, CustomerEntity.Status.confirmed);
        }
        return ckh ?
                ResponseEntity.ok(new ResponseDto().setData(_id).setStatus(HttpStatus.OK).setData("")) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto().setData(_id).setStatus(HttpStatus.BAD_REQUEST).setData("We already called him"));

    }
}
