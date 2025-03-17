package com.profile.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Exchanges {

    private static final String EVENTS_SUFFIX = ".events";
    public static final String PROFILE_EVENTS = "profile" + EVENTS_SUFFIX;
    public static final String DEAD_LETTER_EXCHANGE = "profile.dead-letter" + EVENTS_SUFFIX;
}