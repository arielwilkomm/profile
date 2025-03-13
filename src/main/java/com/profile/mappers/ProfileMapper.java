package com.profile.mappers;

import com.profile.entities.ProfileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import records.profile.ProfileRecord;

@Mapper
public interface ProfileMapper {

    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    @Mapping(target = "cpf", source = "profileEntity.cpf")
    @Mapping(target = "name", source = "profileEntity.name")
    @Mapping(target = "email", source = "profileEntity.email")
    @Mapping(target = "phone", source = "profileEntity.phone")
    ProfileRecord toProfileRecord(ProfileEntity profileEntity);

    @Mapping(target = "cpf", source = "profileRecord.cpf")
    @Mapping(target = "name", source = "profileRecord.name")
    @Mapping(target = "email", source = "profileRecord.email")
    @Mapping(target = "phone", source = "profileRecord.phone")
    @Mapping(target = "firstName", source = "profileRecord.name", qualifiedByName = "extractFirstName")
    @Mapping(target = "lastName", source = "profileRecord.name", qualifiedByName = "extractLastName")
    ProfileEntity toProfileEntity(ProfileRecord profileRecord);

    @Named("extractFirstName")
    default String extractFirstName(String name) {
        return name != null && name.contains(" ") ? name.substring(0, name.indexOf(" ")) : name;
    }

    @Named("extractLastName")
    default String extractLastName(String name) {
        return name != null && name.contains(" ") ? name.substring(name.indexOf(" ") + 1) : "";
    }
}