package br.com.profile.user;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.profile.user.mappers.UserDTOMapper;
import br.com.profile.user.mappers.UserEntityMapper;


@Configuration
public class UserConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addConverter(new UserEntityMapper());
		modelMapper.addConverter(new UserDTOMapper());
		return modelMapper;
	}
}
