package com.profile.records.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.profile.records.address.AddressRecord;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "User's profile", nullable = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ProfileRecord(

        @Schema(description = "User's CPF", example = "12345678900")
        @NotEmpty(message = "CPF cannot be empty")
        @Size(min = 11, max = 11, message = "CPF must be exactly 11 characters long")
        String cpf,

        @Schema(description = "User's full name", example = "John Doe")
        @NotEmpty(message = "Name cannot be empty")
        @Size(max = 120, message = "Name cannot be longer than 120 characters")
        String name,

        @Schema(description = "User's email address", example = "john.doe@email.com")
        @NotEmpty(message = "Email cannot be empty")
        @Size(max = 50, message = "Email cannot be longer than 50 characters")
        String email,

        @Schema(description = "User's phone number", example = "55912345678")
        @NotEmpty(message = "Phone cannot be empty")
        @Size(min = 10, max = 13, message = "Phone number must be between 10 and 13 characters long")
        String phone,

        @Schema(description = "User's addresses")
        List<AddressRecord> addresses
) {
        public ProfileRecord withAddresses(List<AddressRecord> addresses) {
                return new ProfileRecord(cpf, name, email, phone, addresses);
        }
}