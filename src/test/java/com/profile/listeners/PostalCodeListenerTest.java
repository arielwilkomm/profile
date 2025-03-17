package com.profile.listeners;

import com.profile.service.PostalCodeService;
import com.profile.records.viacep.EnderecoRecord;
import com.profile.utils.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.nio.charset.StandardCharsets;

@ExtendWith(MockitoExtension.class)
public class PostalCodeListenerTest {

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
        // Arrange
        EnderecoRecord enderecoRecord = new EnderecoRecord(
                "12345678", "Rua Exemplo", "Complemento", "Unidade", "Bairro Exemplo",
                "Cidade Exemplo", "EX", "Estado Exemplo", "Regiao", "IBGE", "GIA", "DDD", "SIAFI"
        );

        // Simulando a conversão da mensagem para objeto EnderecoRecord
        when(util.parseStringToObject(testPayload, EnderecoRecord.class)).thenReturn(enderecoRecord);

        // Act
        postalCodeListener.postalCodeEvents(message);

        // Assert
        verify(postalCodeService, times(1)).saveEnderecoDocument(enderecoRecord);
    }

    @Test
    void testPostalCodeEvents_Exception() {
        // Arrange
        when(util.parseStringToObject(testPayload, EnderecoRecord.class)).thenThrow(new RuntimeException("Parsing error"));

        // Act & Assert
        try {
            postalCodeListener.postalCodeEvents(message);
        } catch (Exception e) {
            verify(postalCodeService, never()).saveEnderecoDocument(any());
            assertEquals("Parsing error", e.getMessage());
        }
    }
}