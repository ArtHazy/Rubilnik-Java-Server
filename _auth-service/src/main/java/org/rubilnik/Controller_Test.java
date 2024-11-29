package org.rubilnik;

import java.util.Set;

import org.rubilnik.users.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Controller_Test {
    
    @Value("${strings.greeting}")
    String greetingString;

    @GetMapping(value = "/hi")
    ResponseEntity<?> greet(){
        return ResponseEntity.ok().body(new Object(){public String name = "lol";}) ;
    }
}
