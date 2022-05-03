package br.com.profile.user.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.profile.user.entities.UserEntity;

@Repository
@Transactional
public interface UserRepository extends  JpaRepository<UserEntity, String> {

}
