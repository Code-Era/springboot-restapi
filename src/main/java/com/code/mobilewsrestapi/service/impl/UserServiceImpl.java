package com.code.mobilewsrestapi.service.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import com.code.mobilewsrestapi.io.entity.UserEntity;
import org.springframework.stereotype.Service;

import com.code.mobilewsrestapi.io.respository.UserRepository;
import com.code.mobilewsrestapi.service.UserService;
import com.code.mobilewsrestapi.shared.Utils;
import com.code.mobilewsrestapi.shared.dto.UserDto;
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
		
		String id = utils.generateUserId(10);
	
		entity.setUserId(id);
		
		UserEntity storedDetails = userRepository.save(entity);
		UserDto returnedValue = mapper.map(storedDetails, UserDto.class);
		return returnedValue;

	}

	@Override // get Single User
	public UserDto getUser(String userId) {
		
		ModelMapper mapper = new ModelMapper();
		UserEntity entity = userRepository.findByUserId(userId);
				
		UserDto returnedValue = mapper.map(entity, UserDto.class);
		return returnedValue;
	}

	@Override // update use details
	public UserDto updateUser(String userId, UserDto userDto) {
		ModelMapper mapper = new ModelMapper();

		UserEntity entity = userRepository.findByUserId(userId);
		
		userDto.setId(entity.getId());
		userDto.setUserId(entity.getUserId());

		UserEntity serEntity = mapper.map(userDto, UserEntity.class);
		
		UserEntity storedDetails = userRepository.save(serEntity);
	
		
		UserDto returnedValue = mapper.map(storedDetails, UserDto.class);
		return returnedValue;
	}

	@Override
	public void deleteUser(String userId) {
		if(userRepository.findByUserId(userId) != null) {
			userRepository.deleteByUserId(userId);
		}
		
	}
	
	
	
	

}
