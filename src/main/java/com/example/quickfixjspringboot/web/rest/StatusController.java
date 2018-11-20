package com.example.quickfixjspringboot.web.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class StatusController {

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        log.info("-----Call localhost:8080/status-----");
        return new ResponseEntity<>("Up and Running!", HttpStatus.OK);
    }

}
