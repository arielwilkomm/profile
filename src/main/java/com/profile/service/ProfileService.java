package com.profile.service;

import records.profile.ProfileRecord;

public interface ProfileService {

    ProfileRecord getProfile(String cpf);

    ProfileRecord createProfile(ProfileRecord profileRecord);
}
