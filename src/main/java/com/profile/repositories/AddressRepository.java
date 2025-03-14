package com.profile.repositories;

import com.profile.documents.AddressDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends MongoRepository<AddressDocument, ObjectId> {
}
