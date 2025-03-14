package com.profile.service;

import com.profile.entities.ProfileEntity;
import com.profile.mappers.ProfileMapper;
import com.profile.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.profile.records.profile.ProfileRecord;
import com.profile.exceptions.ProfileException;
import com.profile.exceptions.ErrorType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProfileServiceImp implements ProfileService {

    private final ProfileRepository profileRepository;
    private static final ProfileMapper profileMapper = ProfileMapper.INSTANCE;

    private static final String PROFILE_NOT_FOUND ="Profile not found";

    @Autowired
    public ProfileServiceImp(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public ProfileRecord getProfile(String cpf) {
        log.info("Fetching profile");
        try {
            return profileRepository.findById(cpf)
                    .map(profileMapper::toProfileRecord)
                    .orElseThrow(() -> {
                        log.warn(PROFILE_NOT_FOUND);
                        return new ProfileException(ErrorType.PROFILE_NOT_FOUND, PROFILE_NOT_FOUND);
                    });
        } catch (ProfileException e) {
            throw new ProfileException(ErrorType.PROFILE_NOT_FOUND, PROFILE_NOT_FOUND);
        } catch (Exception e) {
            log.error("Unexpected error fetching profile", e);
            throw new ProfileException(ErrorType.INTERNAL_ERROR, "Unexpected error fetching profile", e);
        }
    }

    @Override
    public ProfileRecord createProfile(ProfileRecord profileRecord) {
        log.info("Creating profile");
        try {
            ProfileEntity savedEntity = profileRepository.save(profileMapper.toProfileEntity(profileRecord));
            log.info("Profile created");
            return profileMapper.toProfileRecord(savedEntity);
        } catch (Exception e) {
            log.error("Unexpected error creating profile", e);
            throw new ProfileException(ErrorType.INTERNAL_ERROR, "Unexpected error creating profile", e);
        }
    }

    @Override
    public ProfileRecord updateProfile(String cpf, ProfileRecord profileRecord) {
        log.info("Updating profile");
        try {
            ProfileEntity existingProfile = profileRepository.findById(cpf)
                    .orElseThrow(() -> {
                        log.error(PROFILE_NOT_FOUND);
                        return new ProfileException(ErrorType.PROFILE_NOT_FOUND, "Profile not found for CPF: " + cpf);
                    });

            ProfileEntity updatedProfile = profileMapper.toProfileEntity(profileRecord);
            updatedProfile.setCpf(existingProfile.getCpf());

            ProfileEntity savedProfile = profileRepository.save(updatedProfile);
            log.info("Profile updated");
            return profileMapper.toProfileRecord(savedProfile);
        } catch (ProfileException e) {
            throw new ProfileException(ErrorType.JPA_EXCEPTION, "Unexpected error updating profile", e);
        } catch (Exception e) {
            log.error("Unexpected error updating profile", e);
            throw new ProfileException(ErrorType.INTERNAL_ERROR, "Unexpected error updating profile for CPF: " + cpf, e);
        }
    }

    @Override
    public void deleteProfile(String cpf) {
        log.info("Deleting profile");
        try {
            profileRepository.deleteById(cpf);
            log.info("Profile deleted");
        } catch (Exception e) {
            log.error("Unexpected error deleting profile", e);
            throw new ProfileException(ErrorType.INTERNAL_ERROR, "Unexpected error deleting profile for CPF: " + cpf, e);
        }
    }
}