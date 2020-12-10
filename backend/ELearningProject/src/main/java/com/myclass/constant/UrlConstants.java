package com.myclass.constant;

public class UrlConstants {
	// RequestMapping
	public final static String ADMIN_ROLE = "admin/role";
	public final static String ADMIN_CATEGORY = "admin/category";
	public final static String ADMIN_COURSE = "admin/course";
	public final static String ADMIN_TARGET = "admin/target";
	public final static String ADMIN_USER = "admin/user";
	public final static String ADMIN_VIDEO = "admin/video";
	public final static String LECTURER_HOME = "lecturer/home/index";
	public final static String LECTURER_COURSE = "/lecturer/course";
	public final static String LECURER_HOME_INDEX = "/lecturer";
	public final static String SUB_LECURER_HOME_INDEX = "/sublecturer";

	// LOGIN
	public final static String API_AUTHENTICATION = "authentication/api";
	public final static String LOGIN_URL = "/login";
	public final static String ERROR_NOT_FOUND_URL = "/403";
	public final static String ERROR_NOT_FOUND_PAGE = "error/403";
	public final static String LOGIN_PAGE = "login/index";
	public final static String REDIRECT_LOGIN_PAGE = "redirect:/login";
	public final static String REGISTER_URL = "register";
	public final static String REGISTER_PAGE = "register/index";
	// MAPPING
	public final static String GET = "";
	public final static String ADD = "add";
	public final static String EDIT = "edit";
	public final static String DELETE_ID = "delete/{id}";
	public final static String EDIT_ID = "edit/{id}";
	public final static String VIDEO_ID = "video/{id}";
	public final static String VIDEO_ADD_ID = "video/add/{id}";
	public final static String VIDEO_ADD_IN_COURSE_POST = "video/add";
	public final static String VIDEO_EDIT_IN_COURSE_POST = "video/edit";
	public final static String VIDEO_ADD_IN_COURSE_REDIRECT = "redirect:/admin/course/video/";
	public final static String VIDEO_EDIT_ID = "video/edit/{id}";
	public final static String VIDEO_DELETE_ID = "video/delete/{id}";
	public final static String TARGET_ID = "target/{id}";
	public final static String TARGET_ADD_ID = "target/add/{id}";
	public final static String TARGET_ADD_IN_COURSE_POST = "target/add";
	public final static String TARGET_ADD_IN_COURSE_REDIRECT = "redirect:/admin/course/video/";
	public final static String TARGET_EDIT_ID = "target/edit/{id}";
	public final static String TARGET_EDIT_IN_COURSE_POST = "target/edit";
	public final static String TARGET_DELETE_ID = "target/delete/{id}";
	public final static String HOME_ADMIN_ONE = "admin/home";
	public final static String HOME_ADMIN_SECOND = "admin";
	public final static String LECTURER_COURSE_DETAIL = "detail/{id}";
	public final static String COURSE_DETAIL_OF_LECTURER = "lecturer/course/detail";
	public final static String LECTURER_ADD_COURSE = "lecturer/course/add";
	public final static String LECTURER_EDIT_COURSE = "lecturer/course/edit";
	public final static String LECTURER_ADD_VIDEO_TO_COURSE = "lecturer/course/video_in_course/add";
	public final static String LECTURER_EDIT_VIDEO_TO_COURSE = "lecturer/course/video_in_course/edit";
	public final static String LECTURER_REDIRECT = "redirect:/lecturer";
	public final static String LECTURER_REDIRECT_EDIT_BY_ID = "redirect:/lecturer/course/edit/";
	public final static String LECTURER_REDIRECT_DETAIL_COURSE_BY_ID = "redirect:/lecturer/course/detail/";
	public final static String LECTURER_ADD_TARGET_TO_COURSE = "lecturer/course/target_in_course/add";
	public final static String LECTURER_EDIT_TARGET_TO_COURSE = "lecturer/course/target_in_course/edit";
	public final static String LECTURER_MEMBER_ID = "/member/{id}";
	public final static String LECTURER_DELETE_MEMBER_BY_COURSE_ID = "member/delete/{id}/{courseId}";
	public final static String LECTURER_VIEW_MEMBER_BY_COURSE_ID = "lecturer/course/member_in_course/index";
	public final static String LECTURER_REDIRECT_MEMBER_BY_COURSE_ID = "redirect:/lecturer/course/member/";

	public final static String LECTURER_ADD_MEMBER_BY_COURSE_ID = "member/add/{id}";
	public final static String LECTURER_ADD_MEMBER_TO_COURSE = "lecturer/course/member_in_course/add";
	public final static String LECTURER_ADD_MEMBER_POST = "member/add";
	public final static String AUTHORIZE_MEMBER_BY_COURSE_ID_AND_MEMBER_ID = "member/authorize/{id}/{courseId}";
	public final static String CANCEL_MEMBER_BY_COURSE_ID_AND_MEMBER_ID = "member/cancel/{id}/{courseId}";
	public final static String CART_ID = "cart/{id}";
	public final static String MY_COURSE_ID = "buy_course/{id}";
	public final static String CATEGORY_ID = "category/{id}";
	public final static String USER_CATEGORY_LIST_COURSE = "user/category/index";
	// PATH VARIABLE
	public final static String ID = "id";
	public final static String COURSE_ID = "courseId";
	// ROLE
	public final static String ROLE_LIST = "admin/role/index";
	public final static String ROLE_ADD = "admin/role/add";
	public final static String ROLE_EDIT = "admin/role/edit";
	public final static String ROLE_REDIRECT = "redirect:/admin/role";
	// CATEGORY
	public final static String CATEGORY_LIST = "admin/category/index";
	public final static String CATEGORY_ADD = "admin/category/add";
	public final static String CATEGORY_EDIT = "admin/category/edit";
	public final static String CATEGORY_REDIRECT = "redirect:/admin/category";
	// COURSE
	public final static String COURSE_LIST = "admin/course/index";
	public final static String COURSE_ADD = "admin/course/add";
	public final static String COURSE_EDIT = "admin/course/edit";
	public final static String COURSE_REDIRECT = "redirect:/admin/course";
	public final static String VIDEO_LIST_IN_COURSE = "admin/course/video_in_course/index";
	public final static String VIDEO_ADD_IN_COURSE = "admin/course/video_in_course/add";
	public final static String VIDEO_EDIT_IN_COURSE = "admin/course/video_in_course/edit";
	public final static String VIDEO_LIST_IN_COURSE_BY_COURSE_ID = "redirect:/admin/course/video/";
	public final static String TARGET_LIST_IN_COURSE = "admin/course/target_in_course/index";
	public final static String TARGET_ADD_IN_COURSE = "admin/course/target_in_course/add";
	public final static String TARGET_EDIT_IN_COURSE = "admin/course/target_in_course/edit";
	public final static String TARGET_LIST_IN_COURSE_BY_COURSE_ID = "redirect:/admin/course/target/";
	public final static String USER_COURSE_DETAIL_ID = "detail/{id}";

	public final static String USER_COURSE = "course";
	public final static String USER_COURSE_LIST = "user/course/index";
	public final static String USER_COURSE_DETAIL = "user/course/detail";
	public final static String CLIENT_CART_LIST = "user/client/cart";
	public final static String REDIRECT_USER_COURSE = "redirect:/course/detail/";
	public final static String CLIENT = "client";
	public final static String CLIENT_MY_COURSE = "user/client/my_course";
	public final static String CLIENT_BUY_COURSE_POST = "/buy_course";
	public final static String CLIENT_MY_PROFILE_POST = "/myprofile";
	public final static String CLIENT_MY_COURSE_POST = "/mycourse";
	public final static String CLIENT_MY_PROFILE = "user/client/my_profile";
	public final static String CLIENT_MY_PROFILE_PICTURE_POST = "/myprofile/picture";
	public final static String CLIENT_MY_PROFILE_SECURITY_POST = "/myprofile/security";
	
	public final static String HOMEPAGE = "/home";
	public final static String COURSE_HOME_LIST = "user/home/index";
	public final static String COURSE_HOME_SEARCH = "/search";
	public final static String COURSE_HOME_SEARCH_LIST_COURSE = "user/home/search";
	// TARGET
	public final static String TARGET_LIST = "admin/target/index";
	public final static String TARGET_ADD = "admin/target/add";
	public final static String TARGET_EDIT = "admin/target/edit";
	public final static String TARGET_REDIRECT = "redirect:/admin/target";

	// USER
	public final static String USER_LIST = "admin/user/index";
	public final static String USER_ADD = "admin/user/add";
	public final static String USER_EDIT = "admin/user/edit";
	public final static String USER_REDIRECT = "redirect:/admin/user";
	// VIDEO
	public final static String VIDEO_LIST = "admin/video/index";
	public final static String VIDEO_ADD = "admin/video/add";
	public final static String VIDEO_EDIT = "admin/video/edit";
	public final static String VIDEO_REDIRECT = "redirect:/admin/video";
	// HOME
	public final static String HOMEPAGE_ADMIN = "admin/home/index";
	// CONFIG
	public final static String API_PACKAGE = "com.myclass.api.controller";

	// *** API ** ///
	// RequestMapping

	public final static String API_ADMIN_CATEGOTY = "api/category";
	public final static String API_ADMIN_COURSE = "api/course";
	public final static String API_ADMIN_ROLE = "api/role";
	public final static String API_ADMIN_TARGET = "api/target";
	public final static String API_ADMIN_USER = "api/user";
	public final static String API_ADMIN_VIDEO = "api/video";
	public final static String API_LECTURER_COURSE = "api/lecturer/course";
	public final static String API_FIND_ID = "{id}";
	public final static String API_FIND_COURSE_ID = "{courseId}";
	public final static String API_ADD = "/add";
	public final static String API_EDIT = "/edit";

	// LECTURER API
	public final static String API_LECTURER_ADD_VIDEO_BY_COURSE_ID = "/video/add/{courseId}";
	public final static String API_LECTURER_EDIT_VIDEO_BY_COURSE_ID = "/video/edit/{courseId}";
	public final static String API_LECTURER_FIND_ALL_VIDEO_BY_COURSE_ID = "/video/{courseId}";
	public final static String API_LECTURER_DELETE_VIDEO_BY_COURSE_ID = "/video/{id}";

	public final static String API_LECTURER_ADD_TARGET_BY_COURSE_ID = "/target/add/{courseId}";
	public final static String API_LECTURER_EDIT_TARGET_BY_COURSE_ID = "/target/edit/{courseId}";
	public final static String API_LECTURER_FIND_ALL_TARGET_BY_COURSE_ID = "/target/{courseId}";
	public final static String API_LECTURER_DELETE_TARGET_BY_COURSE_ID = "/target/{id}";

	public final static String API_LECTURER_FIND_ALL_MEMBER_BY_COURSE_ID = "/member/{courseId}";
	public final static String API_LECTURER_ADD_MEMBER_TO_COURSE = "/member/add";
	public final static String API_LECTURER_DELETE_MEMBER_FROM_COURSE = "/member/delete";
	public final static String API_LECTURER_AUTHORIZE_MEMBER_FROM_COURSE = "/member/authorize";
	public final static String API_LECTURER_CANCEL_MEMBER_FROM_COURSE = "/member/cancel";

}
