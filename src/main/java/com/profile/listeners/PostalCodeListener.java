package com.profile.listeners;

import com.profile.constants.Queues;
import com.profile.records.viacep.EnderecoRecord;
import com.profile.service.PostalCodeService;
import com.profile.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class PostalCodeListener {

    private final PostalCodeService postalCodeService;

    @Autowired
    public PostalCodeListener(PostalCodeService postalCodeService) {
        this.postalCodeService = postalCodeService;
    }

    @RabbitListener(queues = Queues.POSTALCODE_EVENT)
    public void postalCodeEvents(final Message message) {
        try {
            String payload = new String(message.getBody(), StandardCharsets.UTF_8);
            log.info("postalCodeEvents - start - payload: {}", payload);

            EnderecoRecord response = (EnderecoRecord) Util.parseStringToObject(payload, EnderecoRecord.class);
            postalCodeService.saveEnderecoDocument(response);

            log.debug("postalCodeEvents - end - payload: {}", payload);
        } catch (Exception ex) {
            log.error("postalCodeEvents - Error - message: {}, error: {}", message, ex.getMessage(), ex);

            throw ex;
        }
    }
}