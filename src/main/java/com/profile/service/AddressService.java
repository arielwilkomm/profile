package com.profile.service;

import com.profile.records.address.AddressRecord;

public interface AddressService {

    AddressRecord getAddressById(String cpf, String addressId);

    AddressRecord createAddress(String cpf, AddressRecord addressRecord);

    AddressRecord updateAddress(String cpf, String addressId, AddressRecord addressRecord);

    void deleteAddress(String cpf, String addressId);
}