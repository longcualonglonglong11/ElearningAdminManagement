package com.myclass.dto;
import javax.validation.constraints.NotBlank;


import org.hibernate.validator.constraints.Length;

public class RegisterDto {
	@NotBlank
	private String email;
	@NotBlank
	@Length(max = 20, min = 6)
	private String password;
	
	@Length(max = 20, min = 6)

	private String confirmPassword;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
}
