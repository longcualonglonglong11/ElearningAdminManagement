package com.myclass.dto;

public class ApiLoginDto {
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
	public ApiLoginDto(String password, String email) {
		super();
		this.password = password;
		this.email = email;
	}
	
}
