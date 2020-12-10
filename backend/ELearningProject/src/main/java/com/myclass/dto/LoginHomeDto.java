package com.myclass.dto;


import org.hibernate.validator.constraints.Length;

public class LoginHomeDto {
	@Length(max = 20, min = 6)
	private String password;
	
	private String email;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LoginHomeDto() {
		
	}
	public LoginHomeDto(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
}
