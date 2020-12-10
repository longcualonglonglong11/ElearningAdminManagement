package com.myclass.controller.register;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myclass.constant.ObjectConstants;
import com.myclass.constant.UrlConstants;
import com.myclass.dto.LoginHomeDto;
import com.myclass.dto.RegisterDto;
import com.myclass.service.RegisterService;
import com.myclass.service.UserService;

@Controller
@RequestMapping(UrlConstants.REGISTER_URL)
public class RegisterController {
	@Autowired
	RegisterService registerService;
	@Autowired
	UserService userService;

	@RequestMapping(value = UrlConstants.GET, method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		modelMap.addAttribute(ObjectConstants.REGISTER_ITEM, new RegisterDto());
		return UrlConstants.REGISTER_PAGE;
	}

	@PostMapping(value = UrlConstants.GET)
	public String save(Model modelMap, @Valid @ModelAttribute(ObjectConstants.REGISTER_ITEM) RegisterDto dto, BindingResult error) {
		if (error.hasErrors()) {
			return UrlConstants.REGISTER_PAGE;
		}
		int result = registerService.register(dto);
		if (result == 2) {
			modelMap.addAttribute(ObjectConstants.MESSAGE_SUCCESS, ObjectConstants.REGISTER_SUCCESS);
			LoginHomeDto homeDto = new LoginHomeDto(dto.getEmail(), dto.getConfirmPassword());
			modelMap.addAttribute(ObjectConstants.LOGIN_ITEM, homeDto);

			return UrlConstants.LOGIN_PAGE;
		}
		if (result == 0)
			modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.REGISTER_FAILURE_CASE_0);
		else if (result == 1)
			modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.REGISTER_FAILURE_CASE_1);
		else
			modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.REGISTER_FAILURE_CASE_2);

		modelMap.addAttribute(ObjectConstants.REGISTER_ITEM, dto);
		return UrlConstants.REGISTER_PAGE;

	}
}
