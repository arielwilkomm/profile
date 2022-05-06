package br.com.profile.user.sevice;

import org.springframework.stereotype.Service;

import br.com.profile.user.dto.UserDTO;

@Service
public interface UserService {

	UserDTO getUser(String userId);

	UserDTO putUser(UserDTO user);

	UserDTO postUser(UserDTO user);

	void deleteUser(String userId);

}
