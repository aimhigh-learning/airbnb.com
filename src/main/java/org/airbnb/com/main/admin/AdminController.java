package org.airbnb.com.main.admin;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * {@link org.springframework.stereotype.Controller} to manage all request for /admin
 * @author sandeep.rana
 */
@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @GetMapping(path = "/welcome")
    public String welcome() {
        return "welcome.html";
    }
}
