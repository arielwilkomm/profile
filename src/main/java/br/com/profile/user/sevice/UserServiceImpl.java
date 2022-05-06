package br.com.profile.user.sevice;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.profile.user.dto.UserDTO;
import br.com.profile.user.entities.UserEntity;
import br.com.profile.user.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	private UserRepository repository;

	@Override
	public UserDTO getUser(String userId) {
		UserEntity entity = repository.getById(userId);
		ObjectMapper mapper = new ObjectMapper();
		UserDTO dto = mapper.convertValue(entity, UserDTO.class);
		return dto;
	}

	@Override
	public UserDTO putUser(UserDTO user) {
		ObjectMapper mapper = new ObjectMapper();
		UserEntity entity = repository.getById(user.getUserId());
		repository.deleteById(user.getUserId());
		entity = mapper.convertValue(user, UserEntity.class);
		UserDTO dto = mapper.convertValue(repository.save(entity), UserDTO.class);
		return dto;
	}

	@Override
	public UserDTO postUser(UserDTO user) {
		ObjectMapper mapper = new ObjectMapper();
		UserEntity entity = mapper.convertValue(user, UserEntity.class);
		UserDTO dto = mapper.convertValue(repository.save(entity), UserDTO.class);
		return dto;
	}

	@Override
	public void deleteUser(String userId) {
		repository.deleteById(userId);
	}

}
