package mg.jovena.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoControlleur {
    @GetMapping("/h")
    public String h() {
        return "hhhh11";
    }
}
