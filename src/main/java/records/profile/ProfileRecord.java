package records.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ProfileRecord(

        @Schema(description = "User's CPF", example = "12345678900")
        String cpf,

        @Schema(description = "User's full name", example = "John Doe")
        String name,

        @Schema(description = "User's first name", example = "John")
        String firstName,

        @Schema(description = "User's last name", example = "Doe")
        String lastName,

        @Schema(description = "User's email address", example = "john.doe@email.com")
        String email,

        @Schema(description = "User's phone number", example = "55912345678")
        String phone

) { }