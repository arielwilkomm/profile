package records.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record ProfileRecord(

        @Schema(description = "User's CPF", example = "12345678900")
        @NotEmpty(message = "CPF cannot be empty")
        String cpf,

        @Schema(description = "User's full name", example = "John Doe")
        @NotEmpty(message = "Name cannot be empty")
        String name,

        @Schema(description = "User's email address", example = "john.doe@email.com")
        @NotEmpty(message = "Email cannot be empty")
        String email,

        @Schema(description = "User's phone number", example = "55912345678")
        @NotEmpty(message = "Phone cannot be empty")
        String phone

) { }