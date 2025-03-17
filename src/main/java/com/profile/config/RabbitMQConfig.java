package com.profile.config;

import com.profile.constants.Exchanges;
import com.profile.constants.Queues;
import com.profile.constants.RabbitMq;
import com.profile.constants.RoutingKeys;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange profileEventsExchange() {
        return new DirectExchange(Exchanges.PROFILE_EVENTS);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(Exchanges.DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public Queue postalCodeQueue() {
        return defaultBuildQueue(Queues.POSTALCODE_EVENT, Queues.POSTALCODE_DEAD);
    }

    @Bean
    public Queue postalCodeDeadLetterQueue() {
        return defaultBuildQueueDead(Queues.POSTALCODE_DEAD);
    }

    @Bean
    public Binding postalCodeBinding() {
        return BindingBuilder.bind(postalCodeQueue()).to(profileEventsExchange()).with(RoutingKeys.POSTALCODE_COMPLETED);
    }

    @Bean
    public Binding postalCodeDeadLetterBinding() {
        return BindingBuilder.bind(postalCodeDeadLetterQueue()).to(deadLetterExchange()).with(RoutingKeys.POSTALCODE_DEAD);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    private Queue defaultBuildQueue(String queueName, String deadName) {
        return QueueBuilder.durable(queueName)
                .withArgument(RabbitMq.XDLE, Exchanges.DEAD_LETTER_EXCHANGE)
                .withArgument(RabbitMq.XDLRK, deadName)
                .build();
    }

    private Queue defaultBuildQueueDead(String deadName) {
        return QueueBuilder.durable(deadName).build();
    }

    @Bean
    public Declarables declarables() {
        return new Declarables(bindings());
    }

    public List<Binding> bindings() {
        return Arrays.asList(
                postalCodeBinding(),
                postalCodeDeadLetterBinding()
        );
    }
}