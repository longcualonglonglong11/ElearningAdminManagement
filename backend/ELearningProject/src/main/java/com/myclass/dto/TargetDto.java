package com.myclass.dto;


import javax.validation.constraints.NotBlank;


public class TargetDto {
	
	private int id;
	@NotBlank()
	private String title;
	private int courseId;
	private String courseName;
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
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = String.valueOf(courseName);
	}
	
}
