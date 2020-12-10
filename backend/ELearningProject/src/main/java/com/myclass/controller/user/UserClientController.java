package com.myclass.controller.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myclass.constant.ObjectConstants;
import com.myclass.constant.UrlConstants;
import com.myclass.dto.CourseDto;
import com.myclass.dto.LoginDto;
import com.myclass.dto.UserDto;
import com.myclass.service.CategoryService;
import com.myclass.service.CloudinaryService;
import com.myclass.service.CourseService;
import com.myclass.service.EnrollmentService;
import com.myclass.service.TargetService;
import com.myclass.service.UserInformationService;
import com.myclass.service.UserService;
import com.myclass.service.VideoService;

@Controller
@RequestMapping(value = { UrlConstants.CLIENT })
public class UserClientController {
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
	@Autowired
	UserService userService;
	@Autowired
	CloudinaryService cloudinaryService;

	@GetMapping(UrlConstants.MY_COURSE_ID)
	
	public String buy(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		int userId = userInformationService.getUserInformation().getId();
		LoginDto account = userInformationService.getUserInformation();
		modelMap.addAttribute(ObjectConstants.ACCOUNT, account);
		CourseDto courseDto = courseService.findDtoById(id);
		double priceOfCourse = (double) (courseDto.getPrice() - (courseDto.getPrice() * courseDto.getDiscount() / 100));
		double restBalance = account.getBalance() - priceOfCourse;
		if (restBalance < 0) {
			modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.NOT_ENOUGH_MONEY);
			modelMap.addAttribute(ObjectConstants.IS_BUY_COURSE, false);

		} else {
			enrollmentService.buyCourse(userId, id, restBalance);
			account.setBalance(restBalance);

			modelMap.addAttribute(ObjectConstants.IS_BUY_COURSE, true);
		}
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.CART_ITEM, enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute(ObjectConstants.VIDEO_LIST, videoService.findAllDtoByCourseId(id));
		modelMap.addAttribute(ObjectConstants.NUMBER_OF_STUDENT, enrollmentService.countEnrollmentInCourse(id));
		modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM, courseService.findCategoryByCourseId(id));
		modelMap.addAttribute(ObjectConstants.TARGET_LIST, targetService.findAllDtoByCourseId(id));
		modelMap.addAttribute(ObjectConstants.COURSE_ITEM, courseDto);

		return UrlConstants.USER_COURSE_DETAIL;
	}

	@RequestMapping(value = UrlConstants.CLIENT_BUY_COURSE_POST, method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		int userId = userInformationService.getId();
		modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.CART_ITEM, enrollmentService.coutCourseInCart(userId));
		return UrlConstants.CLIENT_MY_COURSE;
	}

	@RequestMapping(value = UrlConstants.CLIENT_MY_COURSE_POST, method = RequestMethod.GET)
	public String mycourse(ModelMap modelMap) {
		int userId = userInformationService.getId();
		modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.CART_ITEM, enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute(ObjectConstants.COURSE_LIST, enrollmentService.findListCourseOfUserIsBuy(userId));
		return UrlConstants.CLIENT_MY_COURSE;
	}

	@RequestMapping(value = UrlConstants.CLIENT_MY_PROFILE_POST, method = RequestMethod.GET)
	public String myProfile(ModelMap modelMap) {
		int userId = userInformationService.getId();
		modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.CART_ITEM, enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute(ObjectConstants.COURSE_LIST, enrollmentService.findListCourseOfUserIsBuy(userId));
		modelMap.addAttribute(ObjectConstants.USER_ITEM, userService.findDtoById(userId));
		
		return UrlConstants.CLIENT_MY_PROFILE;
	}

	@PostMapping(value = UrlConstants.CLIENT_MY_PROFILE_POST)
	public String saveProfile(ModelMap modelMap, @Valid @ModelAttribute(ObjectConstants.USER_ITEM) UserDto dto, BindingResult error) {
		int userId = userInformationService.getId();
		modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.CART_ITEM, enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute(ObjectConstants.COURSE_LIST, enrollmentService.findListCourseOfUserIsBuy(userId));
		dto.setId(userId);
		if (error.hasErrors()) {
				
		}
		if (userService.updateProfile(dto)) {
			modelMap.addAttribute(ObjectConstants.MESSGAE_CONTACT, ObjectConstants.UPDATE_PROFILE_SUCCESS);
		}
		LoginDto loginDto = userInformationService.getUserInformation();
		loginDto.setFullname(dto.getFullname());
		UserDto newDto = userService.findDtoById(userId);
		modelMap.addAttribute(ObjectConstants.USER_ITEM, newDto);

		return UrlConstants.CLIENT_MY_PROFILE;
	}

	@RequestMapping(value = UrlConstants.CLIENT_MY_PROFILE_PICTURE_POST)
	public String setPicture(ModelMap modelMap, @Valid @ModelAttribute(ObjectConstants.USER_ITEM) UserDto dto, BindingResult error) {
		int userId = userInformationService.getId();
		modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.CART_ITEM, enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute(ObjectConstants.COURSE_LIST, enrollmentService.findListCourseOfUserIsBuy(userId));
		String cloudinaryImgURL;
		String oldImage = dto.getAvatar();

		if (!cloudinaryService.checkFile(dto.getFileDatas())) {
			cloudinaryImgURL = cloudinaryService.uploadFile(dto.getFileDatas());
		} else {
			cloudinaryImgURL = oldImage;
		}
		dto.setId(userId);
		dto.setAvatar(cloudinaryImgURL);
		if (userService.updateProfilePicture(dto)) {
			modelMap.addAttribute(ObjectConstants.MESSGAE_PICTURE, ObjectConstants.UPDATE_PICTURE_SUCCESS);
		}
		LoginDto loginDto = userInformationService.getUserInformation();
		loginDto.setAvatar(cloudinaryImgURL);
		UserDto newDto = userService.findDtoById(userId);
		modelMap.addAttribute(ObjectConstants.USER_ITEM, newDto);
		return UrlConstants.CLIENT_MY_PROFILE;
	}

	@RequestMapping(value = UrlConstants.CLIENT_MY_PROFILE_SECURITY_POST)
	public String setPassword(ModelMap modelMap, @Valid @ModelAttribute(ObjectConstants.USER_ITEM) UserDto dto,
			  BindingResult error) {
		int userId = userInformationService.getId();
		modelMap.addAttribute(ObjectConstants.ACCOUNT, userInformationService.getUserInformation());
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		modelMap.addAttribute(ObjectConstants.CART_ITEM, enrollmentService.coutCourseInCart(userId));
		modelMap.addAttribute(ObjectConstants.COURSE_LIST, enrollmentService.findListCourseOfUserIsBuy(userId));
			String confirmPass = dto.getConfirmPassword();
		if (confirmPass.equals(dto.getPassword())) {
			
			if(dto.getPassword().length() < 21 && dto.getPassword().length() > 5)
			{
				if (userService.updateSecurity(userId, dto.getPassword())) {
					modelMap.addAttribute(ObjectConstants.MESSGAE_SECURITY, ObjectConstants.UPDATE_SECURITY_SUCCESS);
				}
			}
			else
				modelMap.addAttribute(ObjectConstants.MESSGAE_SECURITY, ObjectConstants.UPDATE_SECURITY_FAILURE_BY_PASSWORD);
		}
		else {
			modelMap.addAttribute(ObjectConstants.MESSGAE_SECURITY, ObjectConstants.UPDATE_SECURITY_FAILURE);
		}
		UserDto newDto = userService.findDtoById(userId);
		modelMap.addAttribute(ObjectConstants.USER_ITEM, newDto);

		return UrlConstants.CLIENT_MY_PROFILE;

	}
}
