package com.myclass.controller.user;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.myclass.constant.ObjectConstants;
import com.myclass.constant.UrlConstants;
import com.myclass.dto.CategoryDto;
import com.myclass.service.CategoryService;
import com.myclass.service.CourseService;
import com.myclass.service.EnrollmentService;
import com.myclass.service.UserInformationService;

@Controller
@RequestMapping(value = { UrlConstants.HOMEPAGE, UrlConstants.GET})
public class UserHomeController {
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
		int userId = userInformationService.getId();
		modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
		modelMap.addAttribute(ObjectConstants.CART_ITEM, enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute(ObjectConstants.COURSE_LIST, courseService.findAllHomeCouse());
		modelMap.addAttribute(ObjectConstants.TOP_DISCOUNT, courseService.findAllHomeCouseDtoOnSale(8));
		modelMap.addAttribute(ObjectConstants.TOP_SELLER, courseService.findAllHomeCouseDtoTopSeller(8));
//		modelMap.addAttribute(ObjectConstants.TOP_SELLER_CHARGED, courseService.findAllChargedHomeCouseDtoTopSeller(8));
//		modelMap.addAttribute(ObjectConstants.TOP_SELLER_FREE, courseService.findAllFreeHomeCouseDtoTopSeller(8));
		List<CategoryDto> categoryDtos = categoryService.findAllDto();
		
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryDtos);
		return UrlConstants.COURSE_HOME_LIST;
	}
	@PostMapping(UrlConstants.COURSE_HOME_SEARCH)
	public String index(Model modelMap, @RequestParam(name = ObjectConstants.QUERRY) String querry) {
		int userId = userInformationService.getUserInformation().getId();
		modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
		modelMap.addAttribute(ObjectConstants.CART_ITEM, enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.QUERRY, querry);
		modelMap.addAttribute(ObjectConstants.COURSE_LIST, courseService.searchCourseByTitle(querry));
		return UrlConstants.COURSE_HOME_SEARCH_LIST_COURSE;
	}
}
