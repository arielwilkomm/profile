package com.profile.publishers;

import com.profile.constants.Exchanges;
import com.profile.constants.RoutingKeys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostalCodePublisherTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private PostalCodePublisher postalCodePublisher;

    private Message message;

    @BeforeEach
    void setUp() {
        message = mock(Message.class); // Mock de uma Message
    }

    @Test
    void testPublishSuccess() {
        String routingKey = RoutingKeys.POSTALCODE_COMPLETED;

        postalCodePublisher.publish(message, routingKey);

        verify(rabbitTemplate, times(1)).convertAndSend(Exchanges.PROFILE_EVENTS, routingKey, message);
    }

    @Test
    void testPublishNullRoutingKey() {
        String routingKey = null;

        postalCodePublisher.publish(message, routingKey);

        verify(rabbitTemplate, times(1)).convertAndSend(Exchanges.PROFILE_EVENTS, routingKey, message);
    }

    @Test
    void testPublishWithDifferentRoutingKey() {
        String routingKey = "custom.routing.key";

        postalCodePublisher.publish(message, routingKey);

        verify(rabbitTemplate, times(1)).convertAndSend(Exchanges.PROFILE_EVENTS, routingKey, message);
    }

    @Test
    void testPublishMessageContent() {
        String routingKey = RoutingKeys.POSTALCODE_COMPLETED;
        String messageContent = "Test Message Content";

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("text/plain");

        Message messages = new Message(messageContent.getBytes(), messageProperties);

        postalCodePublisher.publish(message, routingKey);

        verify(rabbitTemplate, times(1)).convertAndSend(Exchanges.PROFILE_EVENTS, routingKey, messages);
    }

    @Test
    void testPublishShouldLogMessages() {
        String routingKey = RoutingKeys.POSTALCODE_COMPLETED;

        postalCodePublisher.publish(message, routingKey);

        verify(rabbitTemplate, times(1)).convertAndSend(anyString(), anyString(), any(Message.class));
    }
}