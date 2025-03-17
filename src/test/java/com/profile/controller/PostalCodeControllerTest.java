package com.profile.controller;

import com.profile.records.viacep.EnderecoRecord;
import com.profile.service.PostalCodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostalCodeControllerTest {

    @Mock
    private PostalCodeService postalCodeService;

    @InjectMocks
    private PostalCodeController postalCodeController;

    private EnderecoRecord enderecoRecord;

    @BeforeEach
    void setUp() {
        enderecoRecord = new EnderecoRecord("12345-678", "Rua Exemplo", "Complemento Exemplo", "Unidade Exemplo", "Bairro Exemplo", "Cidade Exemplo", "UF", "Estado Exemplo", "Região Exemplo", "1234567", "1234", "11", "123456");
    }

    @Test
    void testGetPostalCode() {
        String postalCode = "12345-678";
        when(postalCodeService.getAddressByPostalCode(postalCode)).thenReturn(enderecoRecord);

        ResponseEntity<EnderecoRecord> response = postalCodeController.getPostalCode(postalCode);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(enderecoRecord, response.getBody());
    }
}