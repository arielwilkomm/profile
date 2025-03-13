package com.profile.service;

import com.profile.entities.ProfileEntity;
import com.profile.mappers.ProfileMapper;
import com.profile.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import records.profile.ProfileRecord;

@Service
public class ProfileServiceImp implements ProfileService {

    private final ProfileRepository profileRepository;
    private static final ProfileMapper profileMapper = ProfileMapper.INSTANCE;

    @Autowired
    public ProfileServiceImp(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public ProfileRecord getProfile(String cpf) {
        return profileRepository.findById(cpf)
                .map(profileMapper::toProfileRecord)
                .orElse(null);
    }

    @Override
    public ProfileRecord createProfile(ProfileRecord profileRecord) {
        ProfileEntity savedEntity = profileRepository.save(profileMapper.toProfileEntity(profileRecord));
        return profileMapper.toProfileRecord(savedEntity);
    }

}
