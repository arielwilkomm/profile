package com.profile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import records.exceptions.ErrorResponseRecords;
import records.profile.ProfileRecord;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping(value = "/v1")
@Tag(name = "Profile", description = "Profile administration")
public class ProfileController {

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
    public ProfileRecord getProfile(@PathVariable("cpf") String cpf) {
        log.info("Getting profile for customer: " + cpf);
        return new ProfileRecord("12345678900", "John Doe", "john.doe@email.com", "55912345678");
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
    public ProfileRecord createProfile(@RequestBody @Valid ProfileRecord profileRecord) {
        log.info("Creating profile for profile: " + profileRecord.cpf());

        return new ProfileRecord(
                profileRecord.cpf(),
                profileRecord.name(),
                profileRecord.email(),
                profileRecord.phone()
        );
    }

    private String[] extractFirstAndLastName(String name) {
        Pattern pattern = Pattern.compile("^([\\w\\-]+)\\s([\\w\\-]+)$");
        Matcher matcher = pattern.matcher(name);

        if (matcher.matches()) {
            return new String[]{matcher.group(1), matcher.group(2)};
        } else {
            return new String[]{name, ""};
        }
    }
}