package br.com.profile.user.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.profile.user.dto.UserDTO;
import br.com.profile.user.sevice.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController(value = "/v1/user/")
public class UserController {

	private UserService userService;

	@GetMapping(value = { "{userId}"}, produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> getUser(@PathVariable("userId") String userId) {
		
		UserDTO user = this.userService.getUser(userId);
		return ResponseEntity.ok(user);
		
	}
	
	@PutMapping(value = { "{userId}"}, produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> putUser(@PathVariable("userId") String userId) {
		
		UserDTO user = this.userService.putUser(userId);
		return ResponseEntity.ok(user);
		
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> postUser(@PathVariable("userId") String userId) {
		
		UserDTO user = this.userService.postUser(userId);
		return ResponseEntity.ok(user);
		
	}

	@GetMapping(value = { "{userId}"}, produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId) {
		
		UserDTO user = this.userService.deleteUser(userId);
		return ResponseEntity.ok().build();
		
	}
	
	
}
