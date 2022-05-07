package br.com.profile.user.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table
public class UserEntity {
	
	@Id
	@Column(name = "user_id")
	private String userId;
	
	private String name;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Column(name = "bith_date")
	private LocalDate bithDate;
	
	private String picture;
	
}
