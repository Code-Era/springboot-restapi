package com.code.mobilewsrestapi.ui.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.code.mobilewsrestapi.io.entity.UserEntity;
import com.code.mobilewsrestapi.io.respository.UserRepository;
import com.code.mobilewsrestapi.service.UserService;
import com.code.mobilewsrestapi.shared.dto.UserDto;
import com.code.mobilewsrestapi.ui.model.request.UserRequestModel;
import com.code.mobilewsrestapi.ui.model.response.user.UserResponse;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping(path = "/users")
	public List<UserResponse> getAllUser() {
		
		List<UserResponse> response = new ArrayList<>();
		List<UserDto> userList = userService.getAllUser();
		
		ModelMapper mapper = new ModelMapper();
		Type typeOfObjectsListNew = new TypeToken<List<UserResponse>>() {}.getType();
		response = mapper.map(userList, typeOfObjectsListNew);
		return response;
	}
	
	

	@PostMapping(path = "/users")
	public UserResponse createUser(@RequestBody UserRequestModel userRequestModel) {
		ModelMapper mapper = new ModelMapper();
		
		UserDto userDto =  mapper.map(userRequestModel,UserDto.class );
		UserDto createdUser = userService.createUserItem(userDto);
		
		UserResponse returnedValue = mapper.map(createdUser, UserResponse.class);
		return returnedValue;
	}
	
	@GetMapping(path = "/users/{userId}")//get single user
	public UserResponse getUser(@PathVariable String userId) {
		
		ModelMapper mapper = new ModelMapper();
		UserDto userDto = userService.getUser(userId);
		
		UserResponse response = mapper.map(userDto, UserResponse.class);
		return response;
	}
	
	
	@PutMapping(path = "/users/{userId}")//get single user
	public UserResponse updateUserDetails(@PathVariable String userId, @RequestBody UserRequestModel model) {
		
		ModelMapper mapper = new ModelMapper();
		
		UserDto userDto =  mapper.map(model, UserDto.class );
		UserDto updateValue = userService.updateUser(userId, userDto);
		
		UserResponse response = mapper.map(updateValue, UserResponse.class);
		return response;
	}
	

	

	@DeleteMapping(path = "/users/{userId}")//get single user
	public void deleteUser(@PathVariable String userId) {
		
		
			userService.deleteUser(userId);
		

	}
	
}
