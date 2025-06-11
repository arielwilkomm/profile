package com.profile.service;

import com.profile.documents.AddressDocument;
import com.profile.documents.EnderecoDocument;
import com.profile.exceptions.ErrorType;
import com.profile.exceptions.ProfileException;
import com.profile.records.address.AddressRecord;
import com.profile.repositories.EnderecoRepository;
import com.profile.repositories.ProfileRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImpTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private PostalCodeService postalCodeService;

    @InjectMocks
    private AddressServiceImp addressService;

    private AddressRecord addressRecord;
    private AddressDocument addressDocument;

    @BeforeEach
    void setUp() {
        addressRecord = AddressRecord.builder()
                .id("67d488e635ede47c5cc0b37e")
                .street("Rua Exemplo")
                .city("Cidade Exemplo")
                .state("Estado Exemplo")
                .country("País Exemplo")
                .postalCode("12345-678")
                .addressType(AddressRecord.AddressType.RESIDENTIAL)
                .build();

        addressDocument = new AddressDocument();
        addressDocument.setStreet("Rua Exemplo");
        addressDocument.setCity("Cidade Exemplo");
        addressDocument.setState("Estado Exemplo");
        addressDocument.setCountry("País Exemplo");
        addressDocument.setPostalCode("12345-678");
        addressDocument.setAddressType(AddressRecord.AddressType.RESIDENTIAL);
    }

    void getAllAddressesReturnsListWhenCpfExists() {
        String cpf = "12345678900";
        List<AddressDocument> addressDocuments = List.of(
                AddressDocument.builder()
                        .street("Rua Exemplo")
                        .city("Cidade Exemplo")
                        .state("Estado Exemplo")
                        .country("País Exemplo")
                        .postalCode("12345-678")
                        .addressType(AddressRecord.AddressType.RESIDENTIAL)
                        .build(),
                AddressDocument.builder()
                        .street("Rua Teste")
                        .city("Cidade Teste")
                        .state("Estado Teste")
                        .country("País Teste")
                        .postalCode("98765-432")
                        .addressType(AddressRecord.AddressType.COMMERCIAL)
                        .build()
        );
        when(profileRepository.existsById(cpf)).thenReturn(true);
        when(mongoTemplate.find(any(Query.class), eq(AddressDocument.class))).thenReturn(addressDocuments);

        List<AddressRecord> result = addressService.getAllAddresses(cpf);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Rua Exemplo", result.get(0).street());
        assertEquals("Rua Teste", result.get(1).street());
    }

    void getAllAddressesThrowsExceptionWhenCpfDoesNotExist() {
        String cpf = "invalidCpf";
        when(profileRepository.existsById(cpf)).thenReturn(false);

        ProfileException exception = assertThrows(ProfileException.class, () -> addressService.getAllAddresses(cpf));

        assertEquals(ErrorType.PROFILE_NOT_FOUND, exception.getErrorType());
        assertEquals("Profile not found", exception.getMessage());
    }

    void getAllAddressesReturnsEmptyListWhenNoAddressesFound() {
        String cpf = "12345678900";
        when(profileRepository.existsById(cpf)).thenReturn(true);
        when(mongoTemplate.find(any(Query.class), eq(AddressDocument.class))).thenReturn(List.of());

        List<AddressRecord> result = addressService.getAllAddresses(cpf);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAddressByIdSuccess() {
        when(profileRepository.existsById("12345678900")).thenReturn(true);
        when(mongoTemplate.findById(new ObjectId("67d488e635ede47c5cc0b37e"), AddressDocument.class)).thenReturn(addressDocument);

        AddressRecord result = addressService.getAddressById("12345678900", "67d488e635ede47c5cc0b37e");

        assertNotNull(result);
        assertEquals(addressRecord.street(), result.street());
        assertEquals(addressRecord.city(), result.city());
        assertEquals(addressRecord.state(), result.state());
    }

    @Test
    void testGetAddressByIdAddressNotFound() {
        when(profileRepository.existsById("12345678900")).thenReturn(true);
        when(mongoTemplate.findById(new ObjectId("67d488e635ede47c5cc0b37e"), AddressDocument.class)).thenReturn(null);

        ProfileException exception = assertThrows(ProfileException.class, () -> {
            addressService.getAddressById("12345678900", "67d488e635ede47c5cc0b37e");
        });

        assertEquals(ErrorType.ADDRESS_NOT_FOUND, exception.getErrorType());
    }

    @Test
    void testCreateAddressSuccess() {
        when(profileRepository.existsById("12345678900")).thenReturn(true);
        when(enderecoRepository.findByCep("12345-678")).thenReturn(java.util.Optional.of(new EnderecoDocument()));
        when(mongoTemplate.save(any(AddressDocument.class))).thenReturn(addressDocument);
        when(postalCodeService.getAddressByPostalCode(anyString()))
                .thenReturn(null);
        AddressRecord result = addressService.createAddress("12345678900", addressRecord);

        assertNotNull(result);
        assertEquals(addressRecord.street(), result.street());
        assertEquals(addressRecord.city(), result.city());
    }

    @Test
    void testCreateAddressProfileNotFound() {
        when(profileRepository.existsById("12345678900")).thenReturn(false);

        ProfileException exception = assertThrows(ProfileException.class, () -> {
            addressService.createAddress("12345678900", addressRecord);
        });

        assertEquals(ErrorType.PROFILE_NOT_FOUND, exception.getErrorType());
    }

    @Test
    void testCreateAddressCepNotFound() {
        when(profileRepository.existsById("12345678900")).thenReturn(true);
        when(enderecoRepository.findByCep("12345-678")).thenReturn(java.util.Optional.empty());
        when(postalCodeService.getAddressByPostalCode(anyString()))
                .thenReturn(null);
        ProfileException exception = assertThrows(ProfileException.class, () -> {
            addressService.createAddress("12345678900", addressRecord);
        });

        assertEquals(ErrorType.CEP_NOT_FOUND, exception.getErrorType());
    }

    @Test
    void testUpdateAddressSuccess() {
        when(profileRepository.existsById("12345678900")).thenReturn(true);
        when(mongoTemplate.exists(any(), eq(AddressDocument.class))).thenReturn(true);
        when(enderecoRepository.findByCep("12345-678")).thenReturn(java.util.Optional.of(new EnderecoDocument()));
        when(mongoTemplate.upsert(any(), any(Update.class), eq(AddressDocument.class))).thenReturn(null);

        AddressRecord result = addressService.updateAddress("12345678900", "67d488e635ede47c5cc0b37e", addressRecord);

        assertNotNull(result);
        assertEquals(addressRecord.street(), result.street());
    }

    @Test
    void testUpdateAddressCepNotFound() {
        when(profileRepository.existsById("12345678900")).thenReturn(true);
        when(mongoTemplate.exists(any(), eq(AddressDocument.class))).thenReturn(true);
        when(enderecoRepository.findByCep("12345-678")).thenReturn(java.util.Optional.empty());

        ProfileException exception = assertThrows(ProfileException.class, () -> {
            addressService.updateAddress("12345678900", "67d488e635ede47c5cc0b37e", addressRecord);
        });

        assertEquals(ErrorType.CEP_NOT_FOUND, exception.getErrorType());
    }

    @Test
    void testDeleteAddressSuccess() {
        when(profileRepository.existsById("12345678900")).thenReturn(true);
        when(mongoTemplate.findOne(any(), eq(AddressDocument.class))).thenReturn(addressDocument);

        assertDoesNotThrow(() -> addressService.deleteAddress("12345678900", "67d488e635ede47c5cc0b37e"));
    }

    @Test
    void testDeleteAddressAddressNotFound() {
        when(profileRepository.existsById("12345678900")).thenReturn(true);
        when(mongoTemplate.findOne(any(), eq(AddressDocument.class))).thenReturn(null);

        ProfileException exception = assertThrows(ProfileException.class, () -> {
            addressService.deleteAddress("12345678900", "67d488e635ede47c5cc0b37e");
        });

        assertEquals(ErrorType.PROFILE_NOT_FOUND, exception.getErrorType());
    }
}