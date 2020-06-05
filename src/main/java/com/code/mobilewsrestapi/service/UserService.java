package com.code.mobilewsrestapi.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.code.mobilewsrestapi.io.entity.UserEntity;
import com.code.mobilewsrestapi.shared.dto.UserDto;


public interface UserService {
	
	public List<UserDto> getAllUser();
	public UserDto createUserItem(UserDto dto);
	public UserDto getUser(String userId);
	public UserDto updateUser(String userId, UserDto userDto);
	public void deleteUser(String userId);

}
