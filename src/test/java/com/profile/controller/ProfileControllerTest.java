package com.profile.controller;

import com.profile.records.profile.ProfileRecord;
import com.profile.records.address.AddressRecord;
import com.profile.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private ProfileController profileController;

    private ProfileRecord profileRecord;

    @BeforeEach
    void setUp() {
        AddressRecord address = new AddressRecord("adrs-123456", "Rua Exemplo", "Cidade Exemplo", "Estado Exemplo", "País Exemplo", "12345-678", AddressRecord.AddressType.RESIDENTIAL);
        profileRecord = new ProfileRecord("12345678900", "Nome Exemplo", "email@example.com", "55912345678", List.of(address));
    }

    @Test
    void testGetProfile() {
        String cpf = "12345678900";
        when(profileService.getProfile(cpf)).thenReturn(profileRecord);

        ResponseEntity<ProfileRecord> response = profileController.getProfile(cpf);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(profileRecord, response.getBody());
    }

    @Test
    void testCreateProfile() {
        when(profileService.createProfile(profileRecord)).thenReturn(profileRecord);

        ResponseEntity<ProfileRecord> response = profileController.createProfile(profileRecord);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertEquals(profileRecord, response.getBody());
    }

    @Test
    void testUpdateProfile() {
        String cpf = "12345678900";
        when(profileService.updateProfile(cpf, profileRecord)).thenReturn(profileRecord);

        ResponseEntity<ProfileRecord> response = profileController.updateProfile(cpf, profileRecord);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(profileRecord, response.getBody());
    }

    @Test
    void testDeleteProfile() {
        String cpf = "12345678900";
        doNothing().when(profileService).deleteProfile(cpf);

        ResponseEntity<Void> response = profileController.deleteProfile(cpf);

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCodeValue());
        verify(profileService, times(1)).deleteProfile(cpf);
    }
}