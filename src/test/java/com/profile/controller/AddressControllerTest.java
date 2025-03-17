package com.profile.controller;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
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