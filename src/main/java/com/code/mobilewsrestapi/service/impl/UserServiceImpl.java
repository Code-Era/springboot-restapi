package com.code.mobilewsrestapi.service.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import com.code.mobilewsrestapi.exception.user.UserServiceException;
import com.code.mobilewsrestapi.exception.user.UserServiceNotFoundException;
import com.code.mobilewsrestapi.io.entity.UserEntity;
import org.springframework.stereotype.Service;

import com.code.mobilewsrestapi.io.respository.UserRepository;
import com.code.mobilewsrestapi.service.UserService;
import com.code.mobilewsrestapi.shared.Utils;
import com.code.mobilewsrestapi.shared.dto.UserDto;
import com.code.mobilewsrestapi.ui.model.response.error.ErrorMessages;
import com.code.mobilewsrestapi.ui.model.response.user.UserResponse;


@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils utils;

	
	
	@Override // Get All user List
	public List<UserDto> getAllUser() {
		
		List<UserEntity> userList = userRepository.findAll();

		Type typeOfObjectsListNew = new TypeToken<List<UserDto>>() {}.getType();
		
		List<UserDto>  userDtoList = new ModelMapper().map(userList, typeOfObjectsListNew);
		return userDtoList;
	}

	@Override // Create User
	public UserDto createUserItem(UserDto userDto) {
		
		ModelMapper mapper = new ModelMapper();
		UserEntity entity = mapper.map(userDto, UserEntity.class);
		
		if(Optional.ofNullable(userRepository.findByUsername(entity.getUsername())).isPresent() ) {
			throw new UserServiceException(entity.getUsername()+" is already exits.");
		}	
		
		String id = utils.generateUserId(10);
	
		entity.setUserId(id);
		
		UserEntity storedDetails = userRepository.save(entity);
		UserDto returnedValue = mapper.map(storedDetails, UserDto.class);
		return returnedValue;

	}

	@Override // get Single User
	public UserDto getUser(String userId) {
		
		ModelMapper mapper = new ModelMapper();
		 Optional<UserEntity> entity = Optional.ofNullable(userRepository.findByUserId(userId));
	
		if( !entity.isPresent()) {
			 throw new EntityNotFoundException("UserId "+userId+" is not found in database.");
		}
				
		UserDto returnedValue = mapper.map(entity.get(), UserDto.class);
		return returnedValue;
		
	}

	@Override // update use details
	public UserDto updateUser(String userId, UserDto userDto) {
		ModelMapper mapper = new ModelMapper();
		
		 Optional<UserEntity> entity = Optional.ofNullable(userRepository.findByUserId(userId));
			
		 if( !entity.isPresent()) {
			 throw new EntityNotFoundException("UserId "+userId+" is not found in database.");
		}
			
		userDto.setId(entity.get().getId());
		userDto.setUserId(entity.get().getUserId());

		UserEntity serEntity = mapper.map(userDto, UserEntity.class);
		
		UserEntity storedDetails = userRepository.save(serEntity);
	
		
		UserDto returnedValue = mapper.map(storedDetails, UserDto.class);
		return returnedValue;
	}

	@Override
	public void deleteUser(String userId) {
		 Optional<UserEntity> entity = Optional.ofNullable(userRepository.findByUserId(userId));
			
		 if( !entity.isPresent()) {
			 throw new EntityNotFoundException("User "+userId+" is not found in database.");
		}
			
		userRepository.deleteByUserId(userId);
		
		
	}
	
	
	
	

}
