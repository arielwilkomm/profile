package com.profile.service;

import com.profile.records.viacep.EnderecoRecord;

public interface PostalCodeService {

    EnderecoRecord getAddressByPostalCode(String postalCode);

    EnderecoRecord saveEnderecoDocument(EnderecoRecord address);
}