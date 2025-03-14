package com.profile.service;

import com.profile.records.profile.ProfileRecord;

public interface ProfileService {

    ProfileRecord getProfile(String cpf);

    ProfileRecord createProfile(ProfileRecord profileRecord);

    ProfileRecord updateProfile(String cpf, ProfileRecord profileRecord);

    void deleteProfile(String cpf);
}
