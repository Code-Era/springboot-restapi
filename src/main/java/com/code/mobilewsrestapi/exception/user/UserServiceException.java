package com.code.mobilewsrestapi.exception.user;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.util.StringUtils;

public class UserServiceException extends RuntimeException {
	
	public UserServiceException(String msg) {
		super(msg);
	}

}
