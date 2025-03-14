package com.profile.controller;

import com.profile.records.viacep.EnderecoRecord;
import com.profile.service.PostalCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/v1/postal-code/{postalCode}")
@Tag(name = "Postal Code", description = "Postal code management")
public class PostalCodeController {

    private final PostalCodeService postalCodeService;

    @Autowired
    public PostalCodeController(PostalCodeService postalCodeService) {
        this.postalCodeService = postalCodeService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get postal code",
            description = "Retrieve postal code information by postal code",
            security = @SecurityRequirement(name = "PassThrough")
    )
    public ResponseEntity<EnderecoRecord> getPostalCode(@PathVariable("postalCode") String postalCode) {
        log.info("getPostalCode - Getting postal code");
        EnderecoRecord address = postalCodeService.getAddressByPostalCode(postalCode);
        return ResponseEntity.ok(address);
    }
}
