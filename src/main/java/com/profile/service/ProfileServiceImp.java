package com.profile.service;

import com.profile.entities.ProfileEntity;
import com.profile.mappers.AddressMapper;
import com.profile.mappers.ProfileMapper;
import com.profile.records.address.AddressRecord;
import com.profile.repositories.AddressRepository;
import com.profile.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.profile.records.profile.ProfileRecord;
import com.profile.exceptions.ProfileException;
import com.profile.exceptions.ErrorType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProfileServiceImp implements ProfileService {

    private final ProfileRepository profileRepository;
    private final AddressRepository addressRepository;
    private final AddressService addressService;
    private static final AddressMapper addressMapper = AddressMapper.INSTANCE;
    private static final ProfileMapper profileMapper = ProfileMapper.INSTANCE;

    private static final String PROFILE_NOT_FOUND ="Profile not found";

    @Autowired
    public ProfileServiceImp(ProfileRepository profileRepository,
                             AddressRepository addressRepository,
                             AddressService addressService) {
        this.addressRepository = addressRepository;
        this.profileRepository = profileRepository;
        this.addressService = addressService;
    }

    @Override
    public ProfileRecord getProfile(String cpf) {
        log.info("getProfile - Fetching profile");
        try {
            ProfileRecord profileRecord = profileRepository.findById(cpf)
                    .map(profileMapper::toProfileRecord)
                    .orElseThrow(() -> {
                        log.warn(PROFILE_NOT_FOUND);
                        return new ProfileException(ErrorType.PROFILE_NOT_FOUND, PROFILE_NOT_FOUND);
                    });
            List<AddressRecord> addresses = addressRepository.findAllByCpf(cpf).stream()
                    .map(addressMapper::toAddressRecord)
                    .collect(Collectors.toList());
            return profileRecord.withAddresses(addresses);
        } catch (ProfileException e) {
            log.error("getProfile - Unexpected error fetching profile", e);
            throw new ProfileException(ErrorType.PROFILE_NOT_FOUND, PROFILE_NOT_FOUND);
        } catch (Exception e) {
            log.error("getProfile - Unexpected error fetching profile", e);
            throw new ProfileException(ErrorType.INTERNAL_ERROR, "Unexpected error fetching profile", e);
        }
    }

    @Override
    public ProfileRecord createProfile(ProfileRecord profileRecord) {
        log.info("createProfile - Creating profile");
        try {
            ProfileEntity savedEntity = profileRepository.save(profileMapper.toProfileEntity(profileRecord));
            log.info("createProfile - Profile created");

            if (profileRecord.addresses() != null && !profileRecord.addresses().isEmpty()) {
                for (AddressRecord address : profileRecord.addresses()) {
                    try {
                        addressService.createAddress(savedEntity.getCpf(), address);
                    } catch (Exception e) {
                        log.error("createProfile - Error creating address", e);
                    }
                }
            }

            return profileMapper.toProfileRecord(savedEntity);
        } catch (ProfileException e) {
            log.error("createProfile - Profile creation failed", e);
            throw new ProfileException(ErrorType.INTERNAL_ERROR, "Unexpected error creating profile", e);
        } catch (Exception e) {
            log.error("createProfile - Unexpected error creating profile", e);
            throw new ProfileException(ErrorType.INTERNAL_ERROR, "Unexpected error creating profile", e);
        }
    }

    @Override
    public ProfileRecord updateProfile(String cpf, ProfileRecord profileRecord) {
        log.info("updateProfile - Updating profile");
        try {
            ProfileEntity existingProfile = profileRepository.findById(cpf)
                    .orElseThrow(() -> {
                        log.error(PROFILE_NOT_FOUND);
                        return new ProfileException(ErrorType.PROFILE_NOT_FOUND, "Profile not found for CPF: " + cpf);
                    });

            ProfileEntity updatedProfile = profileMapper.toProfileEntity(profileRecord);
            updatedProfile.setCpf(existingProfile.getCpf());

            ProfileEntity savedProfile = profileRepository.save(updatedProfile);
            log.info("updateProfile - Profile updated");

            if (profileRecord.addresses() != null) {
                addressRepository.findAllByCpf(cpf).forEach(address ->
                        addressService.deleteAddress(cpf, address.getId())
                );

                for (AddressRecord address : profileRecord.addresses()) {
                    try {
                        addressService.createAddress(cpf, address);
                    } catch (Exception e) {
                        log.error("updateProfile - Error creating address", e);
                    }
                }
            }

            return profileMapper.toProfileRecord(savedProfile);
        } catch (ProfileException e) {
            log.error("updateProfile - Profile update failed", e);
            throw new ProfileException(ErrorType.INTERNAL_ERROR, "Unexpected error updating profile for CPF: " + cpf, e);
        } catch (Exception e) {
            log.error("updateProfile - Unexpected error updating profile", e);
            throw new ProfileException(ErrorType.INTERNAL_ERROR, "Unexpected error updating profile for CPF: " + cpf, e);
        }
    }

    @Override
    public void deleteProfile(String cpf) {
        log.info("deleteProfile - Deleting profile");
        try {
            addressRepository.findAllByCpf(cpf).forEach(address ->
                    addressService.deleteAddress(cpf, address.getId())
            );

            profileRepository.deleteById(cpf);
            log.info("deleteProfile - Profile deleted");
        } catch (Exception e) {
            log.error("deleteProfile - Unexpected error deleting profile", e);
            throw new ProfileException(ErrorType.INTERNAL_ERROR,
                    "Unexpected error deleting profile for CPF: " + cpf, e);
        }
    }
}