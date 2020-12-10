package com.myclass.entity;

import java.io.Serializable;

import com.myclass.constant.ObjectConstants;



public class EnrollmentId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings(ObjectConstants.UNUSE_TYPE)
	private int userId;

	@SuppressWarnings(ObjectConstants.UNUSE_TYPE)
	private int courseId;
}
