package com.profile.mappers;

import com.profile.documents.AddressDocument;
import com.profile.records.address.AddressRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mapping(target = "id", source = "addressDocument.id")
    @Mapping(target = "street", source = "addressDocument.street")
    @Mapping(target = "city", source = "addressDocument.city")
    @Mapping(target = "state", source = "addressDocument.state")
    @Mapping(target = "country", source = "addressDocument.country")
    @Mapping(target = "postalCode", source = "addressDocument.postalCode")
    @Mapping(target = "addressType", source = "addressDocument.addressType")
    AddressRecord toAddressRecord(AddressDocument addressDocument);

    @Mapping(target = "id", source = "addressRecord.id")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "street", source = "addressRecord.street")
    @Mapping(target = "city", source = "addressRecord.city")
    @Mapping(target = "state", source = "addressRecord.state")
    @Mapping(target = "country", source = "addressRecord.country")
    @Mapping(target = "postalCode", source = "addressRecord.postalCode")
    @Mapping(target = "addressType", source = "addressRecord.addressType")
    AddressDocument toAddressDocument(String cpf, AddressRecord addressRecord);
}