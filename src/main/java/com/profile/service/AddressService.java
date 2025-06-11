package com.profile.service;

import com.profile.records.address.AddressRecord;

import java.util.List;

public interface AddressService {

    List<AddressRecord> getAllAddresses(String cpf);

    AddressRecord getAddressById(String cpf, String addressId);

    AddressRecord createAddress(String cpf, AddressRecord addressRecord);

    AddressRecord updateAddress(String cpf, String addressId, AddressRecord addressRecord);

    void deleteAddress(String cpf, String addressId);
}