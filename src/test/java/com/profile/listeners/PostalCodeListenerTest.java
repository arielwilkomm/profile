package com.profile.listeners;

import com.profile.records.viacep.EnderecoRecord;
import com.profile.service.PostalCodeService;
import com.profile.utils.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Message;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostalCodeListenerTest {

    @Mock
    private PostalCodeService postalCodeService;

    @InjectMocks
    private PostalCodeListener postalCodeListener;

    @Mock
    private Util util;

    @Mock
    private Message message;

    private String testPayload;

    @BeforeEach
    void setUp() {
        testPayload = "{\"cep\": \"12345678\", \"logradouro\": \"Rua Exemplo\", \"bairro\": \"Bairro Exemplo\", \"localidade\": \"Cidade Exemplo\", \"uf\": \"EX\"}";
        when(message.getBody()).thenReturn(testPayload.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void testPostalCodeEvents_Success() {
        EnderecoRecord enderecoRecord = new EnderecoRecord(
                "12345678", "Rua Exemplo", "Complemento", "Unidade", "Bairro Exemplo",
                "Cidade Exemplo", "EX", "Estado Exemplo", "Regiao", "IBGE", "GIA", "DDD", "SIAFI"
        );

        when(util.parseStringToObject(testPayload, EnderecoRecord.class)).thenReturn(enderecoRecord);

        postalCodeListener.postalCodeEvents(message);

        verify(postalCodeService, times(1)).saveEnderecoDocument(enderecoRecord);
    }

    @Test
    void testPostalCodeEvents_Exception() {
        when(util.parseStringToObject(testPayload, EnderecoRecord.class)).thenThrow(new RuntimeException("Parsing error"));

        try {
            postalCodeListener.postalCodeEvents(message);
        } catch (Exception e) {
            verify(postalCodeService, never()).saveEnderecoDocument(any());
            assertEquals("Parsing error", e.getMessage());
        }
    }
}