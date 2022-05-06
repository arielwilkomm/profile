package br.com.profile.user.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.profile.user.dto.UserDTO;
import br.com.profile.user.sevice.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = { "/v1/user/{userId}"}, produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> getUser(@PathVariable("userId") String userId) {
		
		UserDTO user = this.userService.getUser(userId);
		return ResponseEntity.ok(user);
		
	}
	
	@PutMapping(value = { "/v1/user"}, produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> putUser(@Valid @RequestBody UserDTO user) {
		
		UserDTO dto = this.userService.putUser(user);
		return ResponseEntity.ok(dto);
		
	}
	
	@PostMapping(value = { "/v1/user"}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> postUser(@Valid @RequestBody UserDTO user) {
		
		UserDTO dto = this.userService.postUser(user);
		return ResponseEntity.ok(dto);
		
	}

	@DeleteMapping(value = { "/v1/user/{userId}"},  produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId) {
		
		this.userService.deleteUser(userId);
		return ResponseEntity.ok().build();
		
	}
	
	
}
