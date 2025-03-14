package com.profile.service;

import com.profile.documents.AddressDocument;
import com.profile.exceptions.ErrorType;
import com.profile.exceptions.ProfileException;
import com.profile.mappers.AddressMapper;
import com.profile.records.address.AddressRecord;
import com.profile.repositories.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AddressServiceImp implements AddressService {

    private final MongoTemplate mongoTemplate;
    private final ProfileRepository profileRepository;
    private static final AddressMapper addressMapper = AddressMapper.INSTANCE;
    private static final String PROFILE_NOT_FOUND = "Profile not found";
    private static final String ADDRESS_NOT_FOUND = "Address not found";

    @Autowired
    public AddressServiceImp(MongoTemplate mongoTemplate, ProfileRepository profileRepository) {
        this.mongoTemplate = mongoTemplate;
        this.profileRepository = profileRepository;
    }

    @Override
    public AddressRecord getAddressById(String cpf, String addressId) {
        log.info("getAddressById - Fetching address");
        AddressDocument addressDocument = mongoTemplate.findById(new ObjectId(addressId), AddressDocument.class);
        if (addressDocument == null) {
            log.warn("getAddressById - Address not found");
            throw new ProfileException(ErrorType.PROFILE_NOT_FOUND, ADDRESS_NOT_FOUND);
        }
        return addressMapper.toAddressRecord(addressDocument);
    }

    @Override
    public AddressRecord createAddress(String cpf, AddressRecord addressRecord) {
        log.info("createAddress - Creating address");
        if (!profileRepository.existsById(cpf)) {
            log.warn(PROFILE_NOT_FOUND);
            throw new ProfileException(ErrorType.PROFILE_NOT_FOUND, PROFILE_NOT_FOUND);
        }
        AddressDocument addressDocument = addressMapper.toAddressDocument(cpf, addressRecord);
        mongoTemplate.save(addressDocument);
        log.info("createAddress - Address created");
        return addressMapper.toAddressRecord(addressDocument);
    }

    @Override
    public AddressRecord updateAddress(String cpf, String addressId, AddressRecord addressRecord) {
        log.info("updateAddress - Updating address");
        Query query = new Query(Criteria.where("_id").is(new ObjectId(addressId)));
        Update update = getUpdateAddressDocument(cpf, addressRecord);
        mongoTemplate.upsert(query, update, AddressDocument.class);
        log.info("updateAddress - Address updated");
        return getAddressById(cpf, addressId);
    }

    @Override
    public void deleteAddress(String cpf, String addressId) {
        log.info("deleteAddress - Deleting address");
        Query query = new Query(Criteria.where("_id").is(new ObjectId(addressId)));
        if (mongoTemplate.findOne(query, AddressDocument.class) == null) {
            log.warn(ADDRESS_NOT_FOUND);
            throw new ProfileException(ErrorType.PROFILE_NOT_FOUND, ADDRESS_NOT_FOUND);
        }
        mongoTemplate.remove(query, AddressDocument.class);
        log.info("deleteAddress - Address deleted");
    }

    private Update getUpdateAddressDocument(String cpf, AddressRecord addressRecord) {
        return new Update()
                .set("cpf", cpf)
                .set("street", addressRecord.street())
                .set("city", addressRecord.city())
                .set("state", addressRecord.state())
                .set("country", addressRecord.country())
                .set("postalCode", addressRecord.postalCode())
                .set("addressType", addressRecord.addressType());
    }
}