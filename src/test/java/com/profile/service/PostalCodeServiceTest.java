package com.profile.service;

import com.profile.clients.ViaCepClient;
import com.profile.constants.RoutingKeys;
import com.profile.documents.EnderecoDocument;
import com.profile.exceptions.ProfileException;
import com.profile.publishers.PostalCodePublisher;
import com.profile.records.viacep.EnderecoRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostalCodeServiceTest {

    @Mock
    private ViaCepClient viaCepClient;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private PostalCodePublisher postalCodePublisher;

    @InjectMocks
    private PostalCodeServiceImp postalCodeService;

    private EnderecoRecord enderecoRecord;

    @BeforeEach
    void setUp() {
        enderecoRecord = new EnderecoRecord("12345678", "Rua Exemplo", "Complemento", "Unidade",
                "Bairro", "Localidade", "UF", "Estado", "Regiao", "123456", "GIA", "DDD", "SIAFI");
    }

    @Test
    void testGetAddressByPostalCode_Success() {
        when(viaCepClient.getEnderecoRecord("12345678")).thenReturn(new ResponseEntity<>(enderecoRecord, HttpStatus.OK));

        EnderecoRecord result = postalCodeService.getAddressByPostalCode("12345678");

        assertNotNull(result);
        assertEquals("12345678", result.cep());
        verify(postalCodePublisher, times(1)).publish(any(Message.class), eq(RoutingKeys.POSTALCODE_COMPLETED));
    }

    @Test
    void testGetAddressByPostalCode_NotFound() {
        when(viaCepClient.getEnderecoRecord("00000000")).thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

        ProfileException exception = assertThrows(ProfileException.class, () -> postalCodeService.getAddressByPostalCode("00000000"));

        assertEquals("Address not found for postal code: 00000000", exception.getMessage());
    }

    @Test
    void testSaveEnderecoDocument() {
        when(mongoTemplate.upsert(any(Query.class), any(Update.class), eq(EnderecoDocument.class)))
                .thenReturn(null);

        EnderecoRecord result = postalCodeService.saveEnderecoDocument(enderecoRecord);

        assertNotNull(result);
        assertEquals("12345678", result.cep());
    }
}