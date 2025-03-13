package com.profile.controller;

import com.profile.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import records.exceptions.ErrorResponseRecords;
import records.profile.ProfileRecord;

@Slf4j
@RestController
@RequestMapping(value = "/v1")
@Tag(name = "Profile", description = "Profile administration")
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
            security = @SecurityRequirement(name = "PassThrough"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Profile retrieved successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProfileRecord.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponseRecords.class)
                            )
                    )
            }
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
            security = @SecurityRequirement(name = "PassThrough"),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Profile created successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProfileRecord.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponseRecords.class)
                            )
                    )
            }
    )
    public ResponseEntity<ProfileRecord> createProfile(@RequestBody @Valid ProfileRecord profileRecord) {
        MDC.put("userId", StringUtils.isNotBlank(profileRecord.cpf()) ? profileRecord.cpf() : "Unknown");

        log.info("createProfile - Creating profile");

        ProfileRecord profile = profileService.createProfile(profileRecord);

        return ResponseEntity.status(HttpStatus.CREATED).body(profile);
    }

}