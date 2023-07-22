package az.company.msuser.controller;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Environment environment;

    public UserController(Environment environment) {
        this.environment = environment;
    }


    @GetMapping
    public String getPort(){
        return "Port number: " + environment.getProperty("local.server.port");
    }

}



