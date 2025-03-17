package com.profile.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RabbitMq {

    public static final String XDLE = "x-dead-letter-exchange";
    public static final String XDLRK = "x-dead-letter-routing-key";
}