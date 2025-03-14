package com.profile.service;

import com.profile.clients.ViaCepClient;
import com.profile.records.viacep.EnderecoRecord;
import com.profile.documents.EnderecoDocument;
import com.profile.exceptions.ProfileException;
import com.profile.exceptions.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PostalCodeServiceImp implements PostalCodeService {

    private final ViaCepClient viaCepClient;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public PostalCodeServiceImp(ViaCepClient viaCepClient, MongoTemplate mongoTemplate) {
        this.viaCepClient = viaCepClient;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public EnderecoRecord getAddressByPostalCode(String postalCode) {
        log.info("getAddressByPostalCode - Fetching address for postal code: {}", postalCode);

        ResponseEntity<EnderecoRecord> response = viaCepClient.getEnderecoRecord(postalCode);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            EnderecoRecord enderecoRecord = response.getBody();
            log.info("getAddressByPostalCode - Address found for postal code: {}", postalCode);

            Update update = getUpdateAddressDocument(enderecoRecord);

            Query query = new Query(Criteria.where("cep").is(postalCode));
            mongoTemplate.upsert(query, update, EnderecoDocument.class);
            log.info("getAddressByPostalCode - Address upserted for postal code: {}", postalCode);

            return enderecoRecord;
        } else {
            log.warn("getAddressByPostalCode - Address not found for postal code: {}", postalCode);
            throw new ProfileException(ErrorType.PROFILE_NOT_FOUND, "Address not found for postal code: " + postalCode);
        }
    }

    private Update getUpdateAddressDocument(EnderecoRecord enderecoRecord) {
        return new Update()
                .set("logradouro", enderecoRecord.logradouro())
                .set("complemento", enderecoRecord.complemento())
                .set("unidade", enderecoRecord.unidade())
                .set("bairro", enderecoRecord.bairro())
                .set("localidade", enderecoRecord.localidade())
                .set("uf", enderecoRecord.uf())
                .set("estado", enderecoRecord.estado())
                .set("regiao", enderecoRecord.regiao())
                .set("ibge", enderecoRecord.ibge())
                .set("gia", enderecoRecord.gia())
                .set("ddd", enderecoRecord.ddd())
                .set("siafi", enderecoRecord.siafi());
    }
}