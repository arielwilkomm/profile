package com.profile.service;

import com.profile.entities.ProfileEntity;
import com.profile.exceptions.ProfileException;
import com.profile.mappers.AddressMapper;
import com.profile.mappers.ProfileMapper;
import com.profile.records.address.AddressRecord;
import com.profile.records.profile.ProfileRecord;
import com.profile.repositories.AddressRepository;
import com.profile.repositories.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImpTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private ProfileServiceImp profileService;

    private ProfileRecord profileRecord;
    private ProfileEntity profileEntity;

    private AddressRecord address;

    @BeforeEach
    void setUp() {
        address = new AddressRecord("adrs-123456", "Rua Exemplo", "Cidade Exemplo", "Estado Exemplo", "País Exemplo", "12345-678", AddressRecord.AddressType.RESIDENCIAL);
        profileRecord = new ProfileRecord("12345678900", "Nome Exemplo", "email@example.com", "55912345678", List.of(address));
        profileEntity = ProfileMapper.INSTANCE.toProfileEntity(profileRecord);
    }

    @Test
    void getProfilesReturnsAllProfilesWithAddresses() {
        when(profileRepository.findAll()).thenReturn(List.of(profileEntity));
        when(addressRepository.findAllByCpf(profileEntity.getCpf())).thenReturn(List.of(AddressMapper.INSTANCE.toAddressDocument(profileEntity.getCpf(), address)));

        List<ProfileRecord> result = profileService.getProfiles();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(profileRecord.cpf(), result.get(0).cpf());
        assertEquals(1, result.get(0).addresses().size());
    }

    @Test
    void getProfilesReturnsEmptyListWhenNoProfilesExist() {
        when(profileRepository.findAll()).thenReturn(List.of());

        List<ProfileRecord> result = profileService.getProfiles();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getProfilesHandlesProfilesWithoutAddresses() {
        when(profileRepository.findAll()).thenReturn(List.of(profileEntity));
        when(addressRepository.findAllByCpf(profileEntity.getCpf())).thenReturn(List.of());

        List<ProfileRecord> result = profileService.getProfiles();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(profileRecord.cpf(), result.get(0).cpf());
        assertTrue(result.get(0).addresses().isEmpty());
    }

    @Test
    void getProfilesThrowsExceptionOnRepositoryError() {
        when(profileRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> profileService.getProfiles());
    }

    @Test
    void testGetProfileSuccess() {
        when(profileRepository.findById("12345678900")).thenReturn(Optional.of(profileEntity));
        when(addressRepository.findAllByCpf("12345678900")).thenReturn(List.of());

        ProfileRecord result = profileService.getProfile("12345678900");

        assertNotNull(result);
        assertEquals(profileRecord.cpf(), result.cpf());
    }

    @Test
    void testGetProfileNotFound() {
        when(profileRepository.findById("12345678900")).thenReturn(Optional.empty());

        assertThrows(ProfileException.class, () -> profileService.getProfile("12345678900"));
    }

    @Test
    void testGetProfileSuccessWithAddresses() {
        when(profileRepository.findById("12345678900")).thenReturn(Optional.of(profileEntity));
        when(addressRepository.findAllByCpf("12345678900")).thenReturn(List.of(AddressMapper.INSTANCE.toAddressDocument("12345678900",address)));

        ProfileRecord result = profileService.getProfile("12345678900");

        assertNotNull(result);
        assertEquals(1, result.addresses().size());
        assertEquals(profileRecord.cpf(), result.cpf());
    }


    @Test
    void testCreateProfile() {
        when(profileRepository.save(any(ProfileEntity.class))).thenReturn(profileEntity);
        when(addressService.createAddress(anyString(), any(AddressRecord.class))).thenReturn(address);

        ProfileRecord result = profileService.createProfile(profileRecord);

        assertNotNull(result);
        assertEquals(profileRecord.cpf(), result.cpf());
    }

    @Test
    void testCreateProfileErrorCreatingAddress() {
        when(profileRepository.save(any(ProfileEntity.class))).thenReturn(profileEntity);
        when(addressService.createAddress(anyString(), any(AddressRecord.class)))
                .thenThrow(new RuntimeException("Error creating address"));

        ProfileRecord result = profileService.createProfile(profileRecord);

        assertNotNull(result);
        assertEquals(profileRecord.cpf(), result.cpf());
    }

    @Test
    void testUpdateProfileSuccess() {
        when(profileRepository.findById("12345678900")).thenReturn(Optional.of(profileEntity));
        when(profileRepository.save(any(ProfileEntity.class))).thenReturn(profileEntity);
        when(addressRepository.findAllByCpf("12345678900")).thenReturn(List.of());
        when(addressService.createAddress(anyString(), any(AddressRecord.class))).thenReturn(address);

        ProfileRecord result = profileService.updateProfile("12345678900", profileRecord);

        assertNotNull(result);
        assertEquals(profileRecord.cpf(), result.cpf());
    }

    @Test
    void testUpdateProfileErrorSavingProfile() {
        when(profileRepository.findById("12345678900")).thenReturn(Optional.of(profileEntity));
        when(profileRepository.save(any(ProfileEntity.class))).thenThrow(new RuntimeException("Error saving profile"));

        assertThrows(ProfileException.class, () -> profileService.updateProfile("12345678900", profileRecord));
    }

    @Test
    void testUpdateProfileNotFound() {
        when(profileRepository.findById("12345678900")).thenReturn(Optional.empty());

        assertThrows(ProfileException.class, () -> profileService.updateProfile("12345678900", profileRecord));
    }

    @Test
    void testDeleteProfileSuccess() {
        when(addressRepository.findAllByCpf("12345678900")).thenReturn(List.of());

        assertDoesNotThrow(() -> profileService.deleteProfile("12345678900"));
    }

    @Test
    void testDeleteProfileError() {
        when(addressRepository.findAllByCpf("12345678900")).thenThrow(new RuntimeException("Error fetching addresses"));

        assertThrows(ProfileException.class, () -> profileService.deleteProfile("12345678900"));
    }

    @Test
    void testCreateProfileNoAddresses() {
        profileRecord = new ProfileRecord("12345678900", "Nome Exemplo", "email@example.com", "55912345678", List.of());
        when(profileRepository.save(any(ProfileEntity.class))).thenReturn(profileEntity);

        ProfileRecord result = profileService.createProfile(profileRecord);

        assertNotNull(result);
        assertEquals(profileRecord.cpf(), result.cpf());
        verify(addressService, times(0)).createAddress(anyString(), any(AddressRecord.class)); // Nenhum endereço deveria ser criado
    }

    @Test
    void testCreateProfileMultipleAddresses() {
        AddressRecord address2 = new AddressRecord("adrs-654321", "Rua Exemplo 2", "Cidade Exemplo 2", "Estado Exemplo 2", "País Exemplo", "23456-789", AddressRecord.AddressType.RESIDENCIAL);
        profileRecord = new ProfileRecord("12345678900", "Nome Exemplo", "email@example.com", "55912345678", List.of(address, address2));
        when(profileRepository.save(any(ProfileEntity.class))).thenReturn(profileEntity);

        ProfileRecord result = profileService.createProfile(profileRecord);

        assertNotNull(result);
        assertEquals(profileRecord.cpf(), result.cpf());
        verify(addressService, times(2)).createAddress(anyString(), any(AddressRecord.class)); // Esperado 2 endereços criados
    }

}