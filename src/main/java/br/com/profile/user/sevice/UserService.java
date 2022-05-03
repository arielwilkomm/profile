package br.com.profile.user.sevice;

import br.com.profile.user.dto.UserDTO;

public interface UserService {

	UserDTO getUser(String userId);

	UserDTO putUser(String userId);

	UserDTO postUser(String userId);

	UserDTO deleteUser(String userId);

}
