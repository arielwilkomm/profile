package com.profile.controller;

import com.profile.exceptions.ErrorType;
import com.profile.exceptions.ProfileException;
import com.profile.records.address.AddressRecord;
import com.profile.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @Mock
    private AddressService addressService;

    @InjectMocks
    private AddressController addressController;

    private AddressRecord addressRecord;

    @BeforeEach
    void setUp() {
        addressRecord = new AddressRecord("adrs-123456", "Rua Exemplo", "Cidade Exemplo", "Estado Exemplo", "País Exemplo", "12345-678", AddressRecord.AddressType.RESIDENTIAL);
    }

    @Test
    void getAllAddressReturnsAddressesWhenCpfExists() {
        String cpf = "12345678900";
        List<AddressRecord> addressRecords = List.of(
            new AddressRecord("adrs-123456", "Rua Exemplo", "Cidade Exemplo", "Estado Exemplo", "País Exemplo", "12345-678", AddressRecord.AddressType.RESIDENTIAL),
            new AddressRecord("adrs-789012", "Rua Teste", "Cidade Teste", "Estado Teste", "País Teste", "98765-432", AddressRecord.AddressType.COMMERCIAL)
        );
        when(addressService.getAllAddresses(cpf)).thenReturn(addressRecords);

        ResponseEntity<List<AddressRecord>> response = addressController.getAllAddress(cpf);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(addressRecords, response.getBody());
    }

    @Test
    void getAllAddressThrowsExceptionWhenCpfDoesNotExist() {
        String cpf = "invalidCpf";
        when(addressService.getAllAddresses(cpf)).thenThrow(new ProfileException(ErrorType.PROFILE_NOT_FOUND, "Profile not found"));

        ProfileException exception = assertThrows(ProfileException.class, () -> addressController.getAllAddress(cpf));

        assertEquals(ErrorType.PROFILE_NOT_FOUND, exception.getErrorType());
        assertEquals("Profile not found", exception.getMessage());
    }

    @Test
    void getAllAddressReturnsEmptyListWhenNoAddressesFound() {
        String cpf = "12345678900";
        when(addressService.getAllAddresses(cpf)).thenReturn(List.of());

        ResponseEntity<List<AddressRecord>> response = addressController.getAllAddress(cpf);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testGetAddress() {
        String cpf = "12345678900";
        String addressId = "adrs-123456";
        when(addressService.getAddressById(cpf, addressId)).thenReturn(addressRecord);

        ResponseEntity<AddressRecord> response = addressController.getAddress(cpf, addressId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(addressRecord, response.getBody());
    }

    @Test
    void testCreateAddress() {
        String cpf = "12345678900";
        when(addressService.createAddress(cpf, addressRecord)).thenReturn(addressRecord);

        ResponseEntity<AddressRecord> response = addressController.createAddress(cpf, addressRecord);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertEquals(addressRecord, response.getBody());
    }

    @Test
    void testUpdateAddress() {
        String cpf = "12345678900";
        String addressId = "adrs-123456";
        when(addressService.updateAddress(cpf, addressId, addressRecord)).thenReturn(addressRecord);

        ResponseEntity<AddressRecord> response = addressController.updateAddress(cpf, addressId, addressRecord);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(addressRecord, response.getBody());
    }

    @Test
    void testDeleteAddress() {
        String cpf = "12345678900";
        String addressId = "adrs-123456";
        doNothing().when(addressService).deleteAddress(cpf, addressId);

        ResponseEntity<Void> response = addressController.deleteAddress(cpf, addressId);

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCodeValue());
        verify(addressService, times(1)).deleteAddress(cpf, addressId);
    }
}