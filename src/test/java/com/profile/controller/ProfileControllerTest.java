package com.profile.controller;

import com.profile.records.address.AddressRecord;
import com.profile.records.profile.ProfileRecord;
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

import static org.junit.jupiter.api.Assertions.*;
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
    void getProfilesEndpointReturnsAllProfilesSuccessfully() {
        when(profileService.getProfiles()).thenReturn(List.of(profileRecord));

        ResponseEntity<List<ProfileRecord>> response = profileController.getProfiles();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(profileRecord.cpf(), response.getBody().get(0).cpf());
    }

    @Test
    void getProfilesEndpointReturnsEmptyListWhenNoProfilesExist() {
        when(profileService.getProfiles()).thenReturn(List.of());

        ResponseEntity<List<ProfileRecord>> response = profileController.getProfiles();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getProfilesEndpointHandlesServiceException() {
        when(profileService.getProfiles()).thenThrow(new RuntimeException("Service error"));

        assertThrows(RuntimeException.class, () -> profileController.getProfiles());
    }

    @Test
    void testGetProfile() {
        String cpf = "12345678900";
        when(profileService.getProfile(cpf)).thenReturn(profileRecord);

        ResponseEntity<ProfileRecord> response = profileController.getProfile(cpf);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(profileRecord, response.getBody());
    }

    @Test
    void testCreateProfile() {
        when(profileService.createProfile(profileRecord)).thenReturn(profileRecord);

        ResponseEntity<ProfileRecord> response = profileController.createProfile(profileRecord);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(profileRecord, response.getBody());
    }

    @Test
    void testUpdateProfile() {
        String cpf = "12345678900";
        when(profileService.updateProfile(cpf, profileRecord)).thenReturn(profileRecord);

        ResponseEntity<ProfileRecord> response = profileController.updateProfile(cpf, profileRecord);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(profileRecord, response.getBody());
    }

    @Test
    void testDeleteProfile() {
        String cpf = "12345678900";
        doNothing().when(profileService).deleteProfile(cpf);

        ResponseEntity<Void> response = profileController.deleteProfile(cpf);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(profileService, times(1)).deleteProfile(cpf);
    }
}