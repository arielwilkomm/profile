package com.profile.mappers;

import com.profile.documents.EnderecoDocument;
import com.profile.records.viacep.EnderecoRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EnderecoMapper {

    EnderecoMapper INSTANCE = Mappers.getMapper(EnderecoMapper.class);

    @Mapping(target = "cep", source = "enderecoDocument.cep")
    @Mapping(target = "logradouro", source = "enderecoDocument.logradouro")
    @Mapping(target = "complemento", source = "enderecoDocument.complemento")
    @Mapping(target = "unidade", source = "enderecoDocument.unidade")
    @Mapping(target = "bairro", source = "enderecoDocument.bairro")
    @Mapping(target = "localidade", source = "enderecoDocument.localidade")
    @Mapping(target = "uf", source = "enderecoDocument.uf")
    @Mapping(target = "estado", source = "enderecoDocument.estado")
    @Mapping(target = "regiao", source = "enderecoDocument.regiao")
    @Mapping(target = "ibge", source = "enderecoDocument.ibge")
    @Mapping(target = "gia", source = "enderecoDocument.gia")
    @Mapping(target = "ddd", source = "enderecoDocument.ddd")
    @Mapping(target = "siafi", source = "enderecoDocument.siafi")
    EnderecoRecord toEnderecoRecord(EnderecoDocument enderecoDocument);

    @Mapping(target = "cep", source = "enderecoRecord.cep")
    @Mapping(target = "logradouro", source = "enderecoRecord.logradouro")
    @Mapping(target = "complemento", source = "enderecoRecord.complemento")
    @Mapping(target = "unidade", source = "enderecoRecord.unidade")
    @Mapping(target = "bairro", source = "enderecoRecord.bairro")
    @Mapping(target = "localidade", source = "enderecoRecord.localidade")
    @Mapping(target = "uf", source = "enderecoRecord.uf")
    @Mapping(target = "estado", source = "enderecoRecord.estado")
    @Mapping(target = "regiao", source = "enderecoRecord.regiao")
    @Mapping(target = "ibge", source = "enderecoRecord.ibge")
    @Mapping(target = "gia", source = "enderecoRecord.gia")
    @Mapping(target = "ddd", source = "enderecoRecord.ddd")
    @Mapping(target = "siafi", source = "enderecoRecord.siafi")
    EnderecoDocument toEnderecoDocument(EnderecoRecord enderecoRecord);
}