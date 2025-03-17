package com.profile.controller;

import com.profile.records.address.AddressRecord;
import com.profile.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/v1/profile/{cpf}")
@Tag(name = "Address", description = "Address management for profiles")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping(value = "/address/{addressId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get address",
            description = "Retrieve address information by CPF and Address ID",
            security = @SecurityRequirement(name = "PassThrough")
    )
    public ResponseEntity<AddressRecord> getAddress(@PathVariable("cpf") String cpf,
                                                    @PathVariable("addressId") String addressId) {
        log.info("getAddress - Getting address");
        AddressRecord address = addressService.getAddressById(cpf, addressId);
        return ResponseEntity.ok(address);
    }

    @PostMapping(value = "/address" ,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create address",
            description = "Create a new address for a profile",
            security = @SecurityRequirement(name = "PassThrough")
    )
    public ResponseEntity<AddressRecord> createAddress(@PathVariable("cpf") String cpf,
                                                       @RequestBody @Valid AddressRecord addressRecord) {
        MDC.put("userId", StringUtils.isNotBlank(cpf) ? cpf : "Unknown");

        log.info("createAddress - Creating address");

        AddressRecord address = addressService.createAddress(cpf, addressRecord);

        return ResponseEntity.status(HttpStatus.CREATED).body(address);
    }

    @PutMapping(value = "/address/{addressId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update address",
            description = "Update an existing address for a profile",
            security = @SecurityRequirement(name = "PassThrough")
    )
    public ResponseEntity<AddressRecord> updateAddress(@PathVariable("cpf") String cpf,
                                                       @PathVariable("addressId") String addressId,
                                                       @RequestBody @Valid AddressRecord addressRecord) {
        log.info("updateAddress - Updating address");
        AddressRecord updatedAddress = addressService.updateAddress(cpf, addressId, addressRecord);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping(value = "/address/{addressId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Delete address",
            description = "Delete an existing address for a profile by CPF and Address ID",
            security = @SecurityRequirement(name = "PassThrough")
    )
    public ResponseEntity<Void> deleteAddress(@PathVariable("cpf") String cpf,
                                              @PathVariable("addressId") String addressId) {
        log.info("deleteAddress - Deleting address");
        addressService.deleteAddress(cpf, addressId);
        return ResponseEntity.noContent().build();
    }
}