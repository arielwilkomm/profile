package br.com.profile.user.mappers;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import br.com.profile.user.dto.UserDTO;
import br.com.profile.user.entities.UserEntity;

public class UserEntityMapper implements Converter<UserDTO, UserEntity> {

	@Override
	public UserEntity convert(MappingContext<UserDTO, UserEntity> context) {
		UserDTO userDTO = context.getSource();
		UserEntity userEntity = context.getDestination() == null ? new UserEntity() : context.getDestination();
		userEntity.setBithDate(userDTO.getBithDate());
		userEntity.setName(userDTO.getName());
		userEntity.setPicture(userDTO.getPicture());
		userEntity.setUserId(userDTO.getUserId());
        return userEntity;
	}
}