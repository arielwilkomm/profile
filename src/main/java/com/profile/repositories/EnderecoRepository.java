package com.profile.repositories;

import com.profile.documents.EnderecoDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnderecoRepository extends MongoRepository<EnderecoDocument, ObjectId> {
    Optional<EnderecoDocument> findByCep(String cep);
}