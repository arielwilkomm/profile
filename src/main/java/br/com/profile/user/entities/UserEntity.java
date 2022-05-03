package br.com.profile.user.entities;

import java.time.LocalDate;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserEntity {
	
	@Column(name = "user_id")
	private String userId;
	
	private String name;
	
	@Column(name = "bith_date")
	private LocalDate bithDate;
	
	private String picture;
	
}
