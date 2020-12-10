package com.myclass.controller.lectuer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myclass.constant.ObjectConstants;
import com.myclass.constant.UrlConstants;
import com.myclass.dto.LoginDto;
import com.myclass.service.CategoryService;
import com.myclass.service.CourseService;
import com.myclass.service.EnrollmentService;
import com.myclass.service.UserInformationService;
@Controller
@RequestMapping(value =  UrlConstants.LECURER_HOME_INDEX)
public class LecturerHomeController {
	@Autowired
	CourseService courseService;
	@Autowired
	EnrollmentService enrollmentService;
	@Autowired
	CategoryService categoryService;
	@Autowired 
	UserInformationService userInformationService;
	@RequestMapping(value = UrlConstants.GET, method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		LoginDto account = userInformationService.getUserInformation();
		modelMap.addAttribute(ObjectConstants.ACCOUNT, account);
		//next
		modelMap.addAttribute(ObjectConstants.COURSE_LIST, courseService.findAllCourseOfLecturer(account.getFullname()));
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		return UrlConstants.LECTURER_HOME;
	}
}
