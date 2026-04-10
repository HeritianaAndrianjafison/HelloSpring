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

    @GetMapping("/hey")
    public String hey() {
        return "hey Heritiana";
    }

    @GetMapping("/herriii")
    public String herriii() {
        return "herriii Heritiana";
    }

    @GetMapping("/tojo")
    public String tojo() {
        return "Hello Tojo";
    }

    @GetMapping("/mp")
    public String mp() {
        return "Hello mpeeesss hihih ";
    }

    @GetMapping("/dg")
    public String dg() {
        return "Hello dg hihih ";
    }

    @GetMapping("/ssh")
    public String ssh() {
        return "Hello ssh ";
    }
    @GetMapping("/ssd")
    public String ssd() {
        return "Hello ssd ";
    }
}
