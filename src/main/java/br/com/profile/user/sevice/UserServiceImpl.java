package br.com.profile.user.sevice;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.profile.user.dto.UserDTO;
import br.com.profile.user.entities.UserEntity;
import br.com.profile.user.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository repository;
	
    @Autowired
    private ModelMapper mapper;

	@Override
	public UserDTO getUser(String userId) {
		UserEntity entity = repository.getById(userId);
		UserDTO dto = this.mapper.map(entity, UserDTO.class);
		return dto;
	}

	@Override
	public UserDTO putUser(UserDTO user) {
		UserEntity entity = repository.getById(user.getUserId());
		repository.deleteById(user.getUserId());
		entity = this.mapper.map(user, UserEntity.class);
		entity = repository.save(entity);
		UserDTO dto = this.mapper.map(entity, UserDTO.class);
		return dto;
	}

	@Override
	public UserDTO postUser(UserDTO user) {
		UserEntity entity = this.mapper.map(user, UserEntity.class);
		entity = repository.save(entity);
		UserDTO dto = this.mapper.map(entity, UserDTO.class);
		return dto;
	}

	@Override
	public void deleteUser(String userId) {
		repository.deleteById(userId);
	}

}
