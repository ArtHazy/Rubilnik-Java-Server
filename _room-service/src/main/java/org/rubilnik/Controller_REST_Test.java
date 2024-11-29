package org.rubilnik;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Controller_REST_Test {
    @GetMapping(value = "/hi")
    String greeting(){
        return "hello";
    }
}
