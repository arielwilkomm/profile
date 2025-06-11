package com.profile.service;

import com.profile.records.profile.ProfileRecord;

import java.util.List;

public interface ProfileService {

    List<ProfileRecord> getProfiles();

    ProfileRecord getProfile(String cpf);

    ProfileRecord createProfile(ProfileRecord profileRecord);

    ProfileRecord updateProfile(String cpf, ProfileRecord profileRecord);

    void deleteProfile(String cpf);
}
