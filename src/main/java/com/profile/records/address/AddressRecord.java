package com.profile.records.address;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
@Schema(description = "User's addresses", nullable = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record AddressRecord(

        @NotBlank(message = "Street name cannot be empty")
        @Size(max = 255, message = "Street name must not exceed 255 characters")
        String street,

        @NotBlank(message = "City cannot be empty")
        @Size(max = 100, message = "City name must not exceed 100 characters")
        String city,

        @NotBlank(message = "State cannot be empty")
        @Size(max = 100, message = "State name must not exceed 100 characters")
        String state,

        @NotBlank(message = "Country cannot be empty")
        @Size(max = 100, message = "Country name must not exceed 100 characters")
        String country,

        @NotBlank(message = "Postal code cannot be empty")
        @Size(max = 20, message = "Postal code must not exceed 20 characters")
        String postalCode,

        @NotNull(message = "Address type cannot be null")
        AddressType addressType
) {

    public enum AddressType {
        RESIDENTIAL,
        COMMERCIAL
    }
}