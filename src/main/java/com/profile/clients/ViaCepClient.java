package com.profile.clients;

import com.profile.records.viacep.EnderecoRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viaCep", url = "${clients.viaCep.url}")
public interface ViaCepClient {

    @GetMapping(path = "/{cep}/json/", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EnderecoRecord> getEnderecoRecord(@PathVariable("cep") String cep);
}