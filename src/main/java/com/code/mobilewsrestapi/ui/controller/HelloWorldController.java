package com.code.mobilewsrestapi.ui.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.mobilewsrestapi.shared.Utils;
import com.code.mobilewsrestapi.ui.model.response.hello.UserDetails;

@RestController
public class HelloWorldController {
	

	
	@GetMapping(path ="/hello-world-bean")
	public UserDetails helloWorld() {
		
		return new UserDetails("Amit", "Singh", "28")
;	}
	

}
