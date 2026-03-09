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

    @GetMapping("/test")
    public String test() {
        return "ok test !! ";
    }

    @GetMapping("/git")
    public String git() {
        return "ok git !! ";
    }

    @GetMapping("/mety")
    public String mety() {
        return "ok mety !! ";
    }

    @GetMapping("/github")
    public String github() {
        return "ok github !! ";
    }

    @GetMapping("/x")
    public String x() {
        return "x github !! ";
    }

    @GetMapping("/y")
    public String y() {
        return "x y !! ";
    }

    @GetMapping("/z")
    public String z() {
        return "x y z !! ";
    }

    @GetMapping("/o")
    public String o() {
        return "ooooooooo !! ";
    }

    @GetMapping("/q")
    public String q() {
        return "qqqqqqq !! ";
    }

    @GetMapping("/r")
    public String r() {
        return "rrrrr !! ";
    }
}