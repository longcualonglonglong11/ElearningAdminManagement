package com.myclass.controller.lectuer.cohost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.myclass.constant.ObjectConstants;
import com.myclass.constant.UrlConstants;
import com.myclass.dto.CourseDto;
import com.myclass.service.CategoryService;
import com.myclass.service.CloudinaryService;
import com.myclass.service.CourseService;
import com.myclass.service.EnrollmentService;
import com.myclass.service.RoleService;
import com.myclass.service.TargetService;
import com.myclass.service.UserInformationService;
import com.myclass.service.UserService;
import com.myclass.service.VideoService;

@Controller
@RequestMapping(value = UrlConstants.SUB_LECURER_HOME_INDEX)
public class CoHostLecturerHomeController {
	@Autowired
	CourseService courseService;
	@Autowired
	EnrollmentService enrollmentService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	TargetService targetService;
	@Autowired
	VideoService videoService;
	@Autowired
	UserInformationService userInformationService;
	@Autowired
	CloudinaryService cloudinaryService;
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;

	@RequestMapping(value = UrlConstants.GET, method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		int userId = userInformationService.getUserInformation().getId();
		modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
		CourseDto dto = courseService.findAllCourseOfSubLecturer(userId);
		int id = dto.getId();
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseService.findDtoById(id));
		modelMap.addAttribute(ObjectConstants.TARGET_LIST, targetService.findAllDtoByCourseId(id));
		modelMap.addAttribute(ObjectConstants.VIDEO_LIST, videoService.findAllDtoByCourseId(id));
		modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM, courseService.findCategoryByCourseId(id));
		modelMap.addAttribute(ObjectConstants.NUMBER_OF_STUDENT, enrollmentService.countEnrollmentInCourse(id));
		return UrlConstants.COURSE_DETAIL_OF_LECTURER;
	}
}
