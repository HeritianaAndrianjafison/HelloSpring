package mg.jovena.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/yes")
    public String sayYes() {
        return "YESSS !! ";
    }

    @GetMapping("/no")
    public String sayNo() {
        return "Noooo !! ";
    }

    @GetMapping("/oui")
    public String sayOui() {
        return "oui !! ";
    }

    @GetMapping("/ok")
    public String sayOk() {
        return "ok !! ";
    }

    @GetMapping("/preprod")
    public String preprod() {
        return "ok preprod !! ";
    }

    @GetMapping("/prod")
    public String prod() {
        return "ok prod !! ";
    }

    @GetMapping("/jenkins")
    public String jenkins() {
        return "ok jenkins !! ";
    }
}