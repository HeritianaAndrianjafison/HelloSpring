package mg.jovena.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloControlleur {
    @GetMapping("/go")
    public String helloWorld() {
        return "Hello Heritiana";
    }

    @GetMapping("/coucou")
    public String coucou() {
        return "coucou Heritiana";
    }
}
