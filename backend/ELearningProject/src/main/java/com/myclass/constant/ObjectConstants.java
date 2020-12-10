package com.myclass.constant;

public class ObjectConstants {
	// MESSAGE
	public final static String MESSAGE = "message";
	public final static String MESSAGE_SUCCESS = "message_success";
	public final static String ERROR_INSERT = "Can't insert ... !";
	public final static String ERROR_UPDATE = "Can't update ... !";
	public final static String ERROR_DELETE = "Can't delete ... !";
	// ROLE
	public final static String ROLE_LIST = "roles";
	public final static String ROLE_ITEM = "role";
	public final static String ROLE_NOT_EXISTS = "A role not exists !";
	// CATEGORY
	public final static String CATEGORY_LIST = "categories";
	public final static String CATEGORY_ITEM = "category";
	public final static String CATEGORY_NOT_EXISTS = "A category not exists !";
	// COURSE
	public final static String COURSE_LIST = "courses";
	public final static String LECTURER_LIST = "lecturers";
	public final static String COURSE_ITEM = "course";
	public final static String COURSE_NOT_EXISTS = "A course not exists !";
	// VIDEO
	public final static String VIDEO_LIST = "videos";
	public final static String VIDEO_ITEM = "video";
	public final static String VIDEO_NOT_EXISTS = "A video not exists !";
	// TARGET
	public final static String TARGET_LIST = "targets";
	public final static String TARGET_ITEM = "target";
	public final static String TARGET_NOT_EXISTS = "A target not exists !";
	// USER
	public final static String USER_LIST = "users";
	public final static String USER_ITEM = "user";
	public final static String USER_NOT_EXISTS = "A user not exists !";
	// UTILITY
	public final static String EMPTY_STRING = "";
	public final static String HASHED_KEY = "$";
	public final static String RAW_TYPE = "rawtypes";
	public final static String UNCHECKED_TYPE = "unchecked";
	public final static String UNUSE_TYPE = "unused";
	public final static String NOT_ENOUGH_MONEY = "Not enough money to buy this course ...!";
	public final static int HASHED_NUMBER = 10;
	public final static String CART_ITEM = "cart";
	public final static String IS_IN_CART = "isInCart";
	public final static String IS_BUY_COURSE = "isBuy";
	public final static String TEACHER = "ROLE_LECTURER";
	public final static String ADMIN = "ROLE_ADMIN";
	public final static String TEACHING_ASSISTANT = "ROLE_SUB_LECTURER";
	public final static String STUDENT = "ROLE_USER";
	public final static String ENROLLMENT_ITEM = "enrollment";
	public final static String LOGIN_ITEM = "login";
	public final static String MESSGAE_CONTACT = "messageContact";
	public final static String UPDATE_PROFILE_SUCCESS = "Update Profile Success";
	public final static String MESSGAE_PICTURE = "messagePicture";
	public final static String UPDATE_PICTURE_SUCCESS = "Update Avatar Success";
	public final static String MESSGAE_SECURITY = "messageSecurity";
	public final static String UPDATE_SECURITY_SUCCESS = "Change Password Success";
	public final static String UPDATE_SECURITY_FAILURE = "Confirm password and password must be same !";
	public final static String LOGIN_FAILURE = "Login failed. Please try again !!!";
	public final static String REGISTER_ITEM = "register";
	public final static String REGISTER_FAILURE_CASE_3 = "Password must from 6 to 20 chacracters";
	public final static String REGISTER_FAILURE_CASE_1 = "Email already exist...";
	public final static String REGISTER_FAILURE_CASE_2 = "Cant register!!!";
	public final static String REGISTER_FAILURE_CASE_0 = "Confirm password and password are not macth...";
	public final static String REGISTER_SUCCESS = "Register success, please login !!!";
	public final static String UPDATE_SECURITY_FAILURE_BY_PASSWORD = "Password must from 6 to 20 characters";
	public final static String COURSE_ALREADY_BUY = "You already bought that course...";
	public final static String TOP_SELLER = "coursesPopular";
	public final static String TOP_DISCOUNT = "coursesOnSale";
	public final static String TOP_SELLER_CHARGED = "coursesPopularCharged";
	public final static String TOP_SELLER_FREE = "coursesPopularFree";
	public final static String QUERRY = "querry";
	public final static String DATE_TIME_FORMAT = "yyyy-MM-dd";
	// LECTURER
	public final static String ACCOUNT = "account";
	public final static String NUMBER_OF_STUDENT = "studentNumber";
	public final static String ID_CO_HOST = "coHostId";
	public final static String KICK_MEMBER_FAILURE = "Can't kik because this user has higher or equal right with you...!";
	public final static String ADD_MEMBER_FAILURE = "Can't add member ... !";
	public final static String AUTHORIZE_MEMBER_FAILURE = "Can't authorize because this user has higher right or equal right with you... !";
	public final static String AUTHORIZE_MEMBER_FAILURE_BY_PERMISSION = "Can't authorize because you don't have permission ... !";
	public static final String CANCEL_MEMBER_FAILURE_BY_PERMISSION = "Can't cancel because you don't have permission ... !";

	// API
	public final static String NOT_FOUND = "NOT FOUND";
	public final static String API_ADD_SUCCESS = "Add success";
	public final static String API_ADD_FAILURE = "Add fail";
	public final static String API_EDIT_SUCCESS = "Edit success";
	public final static String API_EDIT_FAILURE = "Edit fail";
	public final static String API_DELETE_SUCCESS = "Delete success";
	public final static String API_DELETE_FAILURE = "Delete fail";
	public final static String API_LECTURER_EDIT_COURSE_FAILURE = "You just only can modify your course";
	public final static String API_LECTURER_REMOVE_COURSE_FAILURE = "You just only can remove your course";
	public final static String API_LECTURER_ADD_VIDEO_TO_YOUR_COURSE_FAILURE = "You just can only add video to your courses";
	public final static String API_LECTURER_ADD_VIDEO_TO_COURSE_SUCCESS = "Add video to your courses success";
	public final static String API_LECTURER_ADD_VIDEO_TO_COURSE_FAILURE = "Add video to your courses fail";
	public final static String API_LECTURER_EDIT_VIDEO_FROM_YOUR_COURSE_FAILURE = "You just can only modify video in your courses";
	public final static String API_LECTURER_EDIT_VIDEO_FROM_COURSE_SUCCESS = "Edit video from your courses success";
	public final static String API_LECTURER_EDIT_VIDEO_FROM_COURSE_FAILURE = "Edit video from your courses fail";
	public final static String API_LECTURER_DELETE_VIDEO_FROM_YOUR_COURSE_FAILURE = "You just can only remove video in your courses";

	public final static String API_LECTURER_DELETE_VIDEO_FROM_COURSE_SUCCESS = "DELETE video from your courses success";
	public final static String API_LECTURER_DELETE_VIDEO_FROM_COURSE_FAILURE = "DELETE video from your courses fail";
	public final static String API_LECTURER_FIND_ALL_VIDEO_FROM_YOUR_COURSE_FAILURE = "You just can only find all videos in your courses";

	public final static String API_LECTURER_EDIT_TARGET_FROM_YOUR_COURSE_FAILURE = "You just can only modify target in your courses";
	public final static String API_LECTURER_EDIT_TARGET_FROM_COURSE_SUCCESS = "Edit target from your courses success";
	public final static String API_LECTURER_EDIT_TARGET_FROM_COURSE_FAILURE = "Edit target from your courses fail";

	public final static String API_LECTURER_ADD_TARGET_TO_YOUR_COURSE_FAILURE = "You just can only add target in your courses";
	public final static String API_LECTURER_ADD_TARGET_TO_COURSE_SUCCESS = "Add target from your courses success";
	public final static String API_LECTURER_ADD_TARGET_TO_COURSE_FAILURE = "Add target from your courses fail";

	public final static String API_LECTURER_DELETE_TARGET_FROM_YOUR_COURSE_FAILURE = "You just can only delete target in your courses";
	public final static String API_LECTURER_DELETE_TARGET_FROM_COURSE_SUCCESS = "Delete target from your courses success";
	public final static String API_LECTURER_DELETE_TARGET_FROM_COURSE_FAILURE = "Delete target from your courses fail";

	public final static String API_LECTURER_FIND_ALL_TARGET_FROM_YOUR_COURSE_FAILURE = "You just can only add a target in your courses";

	public final static String API_LECTURER_FIND_ALL_MEMBER_FROM_YOUR_COURSE_FAILURE = "You just can only view members in your courses";

	public final static String API_LECTURER_ADD_MEMBER_TO_YOUR_COURSE_FAILURE = "You just can add  members to your courses";
	public final static String API_LECTURER_ADD_MEMBER_TO_COURSE_SUCCESS = "Add member to your course success";
	public final static String API_LECTURER_ADD_MEMBER_TO_COURSE_FAILURE = "This member already in your course";

	public final static String API_LECTURER_KICH_MEMBER_FROM_YOUR_COURSE_FAILURE = "You just only can kich user in your course";
	public final static String API_LECTURER_KICH_MEMBER_FROM_COURSE_SUCCESS = "Kich member from your course success";
	public final static String API_LECTURER_KICH_MEMBER_FROM_COURSE_FAILURE = "Kich member from your course fail";

	public final static String API_LECTURER_KICH_MEMBER_FROM_COURSE_FAILURE_BY_PRIVILEGE = "Can't kich because this user has higher or equal right with you...!";

	public final static String API_LECTURER_AUTHORIZE_MEMBER_FROM_YOUR_COURSE_FAILURE = "You just only can authorize user in your course";
	public final static String API_LECTURER_MEMBER_NOT_IN_YOUR_COURSE = "This user is not in your course";
	public final static String API_LECTURER_AUTHORIZE_MEMBER_FROM_COURSE_FAILURE_BY_PRIVILEGE = "Can't authorize because this user has higher or equal right with you...!";
	public final static String API_LECTURER_AUTHORIZE_CO_HOST_FROM_COURSE_FAILURE_BY_PRIVILEGE = "Can't authorize because this user has already been Co-host";
	public final static String API_LECTURER_AUTHORIZE_MEMBER_FROM_COURSE_SUCCESS = "Authorize member success";
	public final static String API_LECTURER_AUTHORIZE_MEMBER_FROM_COURSE_FAILURE = "Authorize member fail";

	public final static String API_LECTURER_CANCEL_MEMBER_FROM_YOUR_COURSE_FAILURE = "You just only can cancel user in your course";
	public final static String API_LECTURER_CANCEL_MEMBER_FROM_COURSE_FAILURE_BY_PRIVILEGE = "Can't  cancel co-host right because this user has higher or equal right with you...!";
	public final static String API_LECTURER_CANCEL_USER_FROM_COURSE_FAILURE_BY_PRIVILEGE = "Can't cancel co-host right because this member is USER";
	public final static String API_LECTURER_CANCEL_MEMBER_FROM_COURSE_SUCCESS = "Cancel co-host right member success";
	public final static String API_LECTURER_CANCEL_MEMBER_FROM_COURSE_FAILURE = "Cancel co-host right member fail";

	public final static String INFORMATION_1 = "	USER INFORMATION\r\n" + "Email: ";
	public final static String INFORMATION_2 = "\r\n" + "Fullname: ";
	public final static String INFORMATION_3 = "\r\n" + "Role: ";

	public final static String PRIVLEGE = "\r\n	 PRIVILEGE\n";
	public final static String DETAIL_PRIVLEGE_ADMIN = "FIND-ADD-EDIT-DELETE COURSE, USER, TARGET, VIDEO, CATEGORY";
	public final static String DETAIL_PRIVLEGE_TEACHER = "FIND-ADD-EDIT-DELETE COURSE\nFIND-ADD-EDIT-DELETE VIDEO AND TARGET IN YOUR COURSES\nADD-DELETE-AUTHORIZE MEMEBERS IN YOUR COURSE";
	public final static String DETAIL_PRIVLEGE_TEACHING_ASSISTANT = "FIND COURSE\nFIND-ADD-EDIT-DELETE VIDEO AND TARGET IN YOUR COURSES\nADD MEMEBERS TO YOUR COURSE";
	public final static String DETAIL_PRIVLEGE_USER = "FIND ALL COURSES, CATEGORY";
	public final static String NOT_VALID_TOKEN = "TOKEN NOT VALID";
	public final static String HEADER_VALID = "Bearer ";
	public final static String API_KEY = "CYBERSOFT";
	public final static String TOKEN_NO_VERIFICATION = "NO TOKEN";
	public final static String AUTHENTICATION_FAILURE = "Authentication fail\n Please check your email or password";
	public final static String AUTHORIZATION_HEADER = "Authorization";
}
