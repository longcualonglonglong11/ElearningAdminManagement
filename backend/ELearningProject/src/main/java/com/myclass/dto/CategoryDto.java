package com.myclass.dto;

import javax.validation.constraints.NotBlank;

public class CategoryDto {
	private int id;
	@NotBlank
	private String title;
	@NotBlank
	private String icon;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
