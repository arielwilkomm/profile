package com.profile.controller;

import com.profile.service.ProfileService;
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
import com.profile.records.profile.ProfileRecord;

@Slf4j
@RestController
@RequestMapping(value = "/v1")
@Tag(name = "Profile", description = "Operations related to user profiles")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping(value = "/profile/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get profile",
            description = "Retrieve profile information by CPF",
            security = @SecurityRequirement(name = "PassThrough")
    )
    public ResponseEntity<ProfileRecord> getProfile(@PathVariable("cpf") String cpf) {
        log.info("getProfile - Getting profile");
        ProfileRecord profile = profileService.getProfile(cpf);
        return ResponseEntity.ok(profile);
    }

    @PostMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create profile",
            description = "Create a new profile information",
            security = @SecurityRequirement(name = "PassThrough")
    )
    public ResponseEntity<ProfileRecord> createProfile(@RequestBody @Valid ProfileRecord profileRecord) {
        MDC.put("userId", StringUtils.isNotBlank(profileRecord.cpf()) ? profileRecord.cpf() : "Unknown");

        log.info("createProfile - Creating profile");

        ProfileRecord profile = profileService.createProfile(profileRecord);

        return ResponseEntity.status(HttpStatus.CREATED).body(profile);
    }

    @PutMapping(value = "/profile/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update profile",
            description = "Update an existing profile",
            security = @SecurityRequirement(name = "PassThrough")
    )
    public ResponseEntity<ProfileRecord> updateProfile(@PathVariable("cpf") String cpf, @RequestBody @Valid ProfileRecord profileRecord) {
        log.info("updateProfile - Updating profile");
        ProfileRecord updatedProfile = profileService.updateProfile(cpf, profileRecord);
        return ResponseEntity.ok(updatedProfile);
    }

    @DeleteMapping(value = "/profile/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Delete profile",
            description = "Delete an existing profile by CPF",
            security = @SecurityRequirement(name = "PassThrough")
    )
    public ResponseEntity<Void> deleteProfile(@PathVariable("cpf") String cpf) {
        log.info("deleteProfile - Deleting profile");
        profileService.deleteProfile(cpf);
        return ResponseEntity.noContent().build();
    }

}