package com.myclass.controller.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myclass.constant.ObjectConstants;
import com.myclass.constant.UrlConstants;
import com.myclass.dto.CourseDto;
import com.myclass.dto.EnrollmentDto;
import com.myclass.dto.LoginDto;
import com.myclass.service.CategoryService;
import com.myclass.service.CourseService;
import com.myclass.service.EnrollmentService;
import com.myclass.service.TargetService;
import com.myclass.service.UserInformationService;
import com.myclass.service.VideoService;

@Controller
@RequestMapping(value = { UrlConstants.USER_COURSE })
public class UserCourseController {
	@Autowired
	CourseService courseService;
	@Autowired
	TargetService targetService;
	@Autowired
	VideoService videoService;
	@Autowired
	EnrollmentService enrollmentService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	UserInformationService userInformationService;

	@RequestMapping(value = UrlConstants.GET, method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		int userId = userInformationService.getUserInformation().getId();
		modelMap.addAttribute(ObjectConstants.CART_ITEM, enrollmentService.coutCourseInCart(userId));
		return UrlConstants.USER_COURSE_LIST;
	}

	@GetMapping(UrlConstants.USER_COURSE_DETAIL_ID)
	public String index(Model modelMap, @PathVariable(UrlConstants.ID) int id) {

		int userId = userInformationService.getUserInformation().getId();
		EnrollmentDto dto = enrollmentService.findDto(userId, id);
		if (dto == null) {
			dto = new EnrollmentDto();
			dto.setInCart(false);
			dto.setBuy(false);
		}
		modelMap.addAttribute(ObjectConstants.CART_ITEM, enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
		modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseService.findDtoById(id));
		modelMap.addAttribute(ObjectConstants.TARGET_LIST, targetService.findAllDtoByCourseId(id));
		modelMap.addAttribute(ObjectConstants.VIDEO_LIST, videoService.findAllDtoByCourseId(id));
		modelMap.addAttribute(ObjectConstants.IS_IN_CART, dto.isInCart());
		modelMap.addAttribute(ObjectConstants.IS_BUY_COURSE, dto.isBuy());
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM, courseService.findCategoryByCourseId(id));
		modelMap.addAttribute(ObjectConstants.NUMBER_OF_STUDENT, enrollmentService.countEnrollmentInCourse(id));
		return UrlConstants.USER_COURSE_DETAIL;
	}

	@GetMapping(UrlConstants.CART_ID)
	public String indexCart(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		int userId = userInformationService.getUserInformation().getId();
		modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());

		EnrollmentDto dto = enrollmentService.findDto(userId, id);
		if (dto != null && dto.isInCart()) {
			enrollmentService.removeCart(userId, id);
		} else {
			enrollmentService.enroll(userId, id);

		}
		return UrlConstants.REDIRECT_USER_COURSE + id;
	}

	@GetMapping(UrlConstants.MY_COURSE_ID)
	public String buy(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		int userId = userInformationService.getUserInformation().getId();
		LoginDto account = userInformationService.getUserInformation();
		modelMap.addAttribute(ObjectConstants.ACCOUNT, account);	
		CourseDto courseDto = courseService.findDtoById(id);
		EnrollmentDto dto = enrollmentService.findDto(userId, id);

		double priceOfCourse = (double) (courseDto.getPrice() - (courseDto.getPrice() * courseDto.getDiscount() / 100));
		double restBalance = account.getBalance() - priceOfCourse;
		if(dto != null && dto.isBuy()){
			modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.COURSE_ALREADY_BUY);
			modelMap.addAttribute(ObjectConstants.IS_BUY_COURSE, true);
		}
		
		else if (restBalance < 0) {
			modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.NOT_ENOUGH_MONEY);
			modelMap.addAttribute(ObjectConstants.IS_BUY_COURSE, false);
		
		} else {
			enrollmentService.buyCourse(userId, id, restBalance);
			account.setBalance(restBalance);

			modelMap.addAttribute(ObjectConstants.IS_BUY_COURSE, true);
		}
		if (dto != null)
			modelMap.addAttribute(ObjectConstants.IS_IN_CART, dto.isInCart());
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.CART_ITEM, enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute(ObjectConstants.VIDEO_LIST, videoService.findAllDtoByCourseId(id));
		modelMap.addAttribute(ObjectConstants.NUMBER_OF_STUDENT, enrollmentService.countEnrollmentInCourse(id));
		modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM, courseService.findCategoryByCourseId(id));
		modelMap.addAttribute(ObjectConstants.TARGET_LIST, targetService.findAllDtoByCourseId(id));
		modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseDto);
		return UrlConstants.USER_COURSE_DETAIL;
	}

	@GetMapping(ObjectConstants.CART_ITEM)
	public String cart(ModelMap modelMap) {
		int userId = userInformationService.getUserInformation().getId();
		modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());

		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.CART_ITEM, enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute(ObjectConstants.COURSE_LIST, enrollmentService.findListCourseOfUserInCart(userId));
		return UrlConstants.CLIENT_CART_LIST;
	}

	@GetMapping(UrlConstants.CATEGORY_ID)
	public String indexCategory(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
		int userId = userInformationService.getUserInformation().getId();
		modelMap.addAttribute(ObjectConstants.CART_ITEM, enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM, categoryService.findDtoById(id));

		modelMap.addAttribute(ObjectConstants.COURSE_LIST, courseService.findAllDtoByCategoryId(id));
		return UrlConstants.USER_CATEGORY_LIST_COURSE;
	}

}
