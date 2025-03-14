package com.profile.exceptions;

import java.util.List;

public record ErrorResponseRecords(
        int status,
        String message,
        List<String> details
) { }
