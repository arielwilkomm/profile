package records.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Error response model")
public record ErrorResponseRecords(
        @Schema(description = "HTTP status code", example = "500")
        int status,

        @Schema(description = "Error message", example = "Internal Server Error")
        String message,

        @Schema(description = "Detailed error description", example = "An unexpected error occurred while processing the request.")
        String details
) { }
