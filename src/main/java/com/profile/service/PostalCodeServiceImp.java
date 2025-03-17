package com.profile.service;

import com.profile.clients.ViaCepClient;
import com.profile.constants.RoutingKeys;
import com.profile.publishers.PostalCodePublisher;
import com.profile.records.viacep.EnderecoRecord;
import com.profile.documents.EnderecoDocument;
import com.profile.exceptions.ProfileException;
import com.profile.exceptions.ErrorType;
import com.profile.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class PostalCodeServiceImp implements PostalCodeService {

    private final ViaCepClient viaCepClient;
    private final MongoTemplate mongoTemplate;
    private final PostalCodePublisher postalCodePublisher;
    private final Util util;

    @Autowired
    public PostalCodeServiceImp(ViaCepClient viaCepClient, MongoTemplate mongoTemplate, PostalCodePublisher postalCodePublisher, Util util) {
        this.viaCepClient = viaCepClient;
        this.mongoTemplate = mongoTemplate;
        this.postalCodePublisher = postalCodePublisher;
        this.util = util;
    }

    @Override
    @Cacheable(value = "postalCodeCache", key = "#postalCode")
    public EnderecoRecord getAddressByPostalCode(String postalCode) {
        log.info("getAddressByPostalCode - Fetching address for postal code: {}", postalCode);

        ResponseEntity<EnderecoRecord> response = viaCepClient.getEnderecoRecord(postalCode);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            String parseMessage = util.parseObjectToJson(response.getBody());
            Message message = MessageBuilder.withBody(parseMessage.getBytes(StandardCharsets.UTF_8)).build();
            postalCodePublisher.publish(message, RoutingKeys.POSTALCODE_COMPLETED);
            return response.getBody();
        } else {
            log.warn("getAddressByPostalCode - Address not found for postal code: {}", postalCode);
            throw new ProfileException(ErrorType.PROFILE_NOT_FOUND, "Address not found for postal code: " + postalCode);
        }
    }

    public EnderecoRecord saveEnderecoDocument(EnderecoRecord address) {
        log.info("getAddressByPostalCode - Address found for postal code: {}", address.cep());

        Update update = getUpdateAddressDocument(address);

        Query query = new Query(Criteria.where("cep").is(address.cep()));
        mongoTemplate.upsert(query, update, EnderecoDocument.class);
        log.info("getAddressByPostalCode - Address upserted for postal code: {}", address.cep());

        return address;
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