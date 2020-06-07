package com.code.mobilewsrestapi.ui.model.request;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CollectionId;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.RequestParam;

import com.code.mobilewsrestapi.ui.model.response.error.ErrorMessages;
;




public class UserRequestModel implements Serializable {

	@Autowired
	ErrorMessages errorMessage;
	
	@NotBlank(message = "username field is required.")
	private String firstName;
	
	@NotBlank(message = "username field is required.")
	private String username;
	
	@NotBlank(message = "LastName field is required.")
	private String lastName;
	
	@NotBlank(message = "email field is required.")
	private String email;
	
	@NotBlank(message = "role field is required.")
	private String role;
	
	@NotBlank(message = "ssn field is required.")
	private String ssn;
	
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}


}
