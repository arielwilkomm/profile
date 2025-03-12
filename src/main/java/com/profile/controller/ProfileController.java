package com.profile.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/v1")
public class ProfileController {

    @GetMapping(value = "/profile/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean getProfile(@PathVariable("cpf") String cpf) {
        log.info("Getting profile for customer: " + cpf);
        return true;
    }
}
