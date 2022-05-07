package br.com.profile.user.mappers;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import br.com.profile.user.dto.UserDTO;
import br.com.profile.user.entities.UserEntity;

public class UserEntityMapper implements Converter<UserDTO, UserEntity> {

	@Override
	public UserEntity convert(MappingContext<UserDTO, UserEntity> context) {
		UserDTO userDTO = context.getSource();
		UserEntity userEntity = context.getDestination();
		userDTO.setBithDate(userEntity.getBithDate());
		userDTO.setName(userEntity.getName());
		userDTO.setPicture(userEntity.getPicture());
		userDTO.setUserId(userEntity.getUserId());
        return userEntity;
	}
}