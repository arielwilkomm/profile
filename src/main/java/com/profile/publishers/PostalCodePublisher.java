package com.profile.publishers;

import com.profile.constants.Exchanges;
import com.profile.constants.RoutingKeys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PostalCodePublisher {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public PostalCodePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(final Message message, final String routingKey) {
        log.info("FrontMissionPublisher.publish - Start - Sending message, in routingKey: [{}], message: [{}]",
                RoutingKeys.POSTALCODE_COMPLETED, message);

        rabbitTemplate.convertAndSend(Exchanges.PROFILE_EVENTS, routingKey, message);

        log.info("FrontMissionPublisher.publish - End - Sending message, in routingKey: [{}]", routingKey);
    }

}