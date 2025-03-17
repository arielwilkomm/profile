package com.profile.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoutingKeys {

    private static final String COMPLETED_SUFFIX = ".completed";
    private static final String DEAD_SUFFIX = ".dead";

    public static final String POSTALCODE_COMPLETED = "postalCode" + COMPLETED_SUFFIX;
    public static final String POSTALCODE_DEAD = "postalCode" + DEAD_SUFFIX;
}