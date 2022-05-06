package br.com.profile.user.dto;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String userId;
	
	private String name;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate bithDate;
	
	private String picture;
	
}
