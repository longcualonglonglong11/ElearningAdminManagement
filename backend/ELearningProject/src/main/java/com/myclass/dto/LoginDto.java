package com.myclass.dto;

import java.util.Collection;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * @author Long
 *
 */
public class LoginDto extends User implements UserDetails {

	/**
	 * 
	 */
	
	private String email;
	private int id;
	private String fullname;
	private String avatar;
	private String password;
	private double balance;
	int roleId;
	private String roleName;
	private String roleDescription;
	private static final long serialVersionUID = 1L;

	public LoginDto(String email, String password, Collection<? extends GrantedAuthority> authorities, int id, String fullname, String avatar, double balance, int roleId, String roleName, String roleDescription) {
		super(email, password, authorities);
		this.id = id;
		this.fullname = fullname;
		this.avatar = avatar;
		this.password = password;
		this.roleId = roleId;
		double roundedDouble = Math.round(balance * 100.0) / 100.0;
		this.roleName = roleName;
		this.balance = roundedDouble;
		this.email = email;
		this.roleDescription = roleDescription;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		double roundedDouble = Math.round(balance * 100.0) / 100.0;

		this.balance = roundedDouble;	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

//	@Override
//	public String toString() {
//		return "LoginDto [email=" + email + ", id=" + id + ", fullname=" + fullname + ", avatar=" + avatar
//				+ ", password=" + password + ", balance=" + balance + ", roleId=" + roleId + ", roleName=" + roleName
//				+ ", roleDescription=" + roleDescription + "]";
//	}
	
	

}
