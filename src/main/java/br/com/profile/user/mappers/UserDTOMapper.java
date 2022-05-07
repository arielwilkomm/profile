package br.com.profile.user.mappers;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import br.com.profile.user.dto.UserDTO;
import br.com.profile.user.entities.UserEntity;

public class UserDTOMapper implements Converter<UserEntity, UserDTO> {

	@Override
	public UserDTO convert(MappingContext<UserEntity, UserDTO> context) {
		UserDTO userDTO = context.getDestination() == null ? new UserDTO() : context.getDestination();
		UserEntity userEntity = context.getSource();
		userDTO.setBithDate(userEntity.getBithDate());
		userDTO.setName(userEntity.getName());
		userDTO.setPicture(userEntity.getPicture());
		userDTO.setUserId(userEntity.getUserId());
        return userDTO;
	}
}