package com.profile.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Queues {

    private static final String PROFILE_PREFIX = "profile.";
    private static final String PROFILE_SUFFIX = ".events";

    public static final String POSTALCODE_EVENT = PROFILE_PREFIX + "postalCodeEvent" + PROFILE_SUFFIX;
    public static final String POSTALCODE_DEAD = PROFILE_PREFIX + "postalCodeEvent.dead" + PROFILE_SUFFIX;
}