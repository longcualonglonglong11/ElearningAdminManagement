package com.myclass.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myclass.constant.ObjectConstants;
import com.myclass.constant.UrlConstants;
import com.myclass.dto.LoginHomeDto;

@Controller
@RequestMapping(UrlConstants.LOGIN_URL)
public class LoginController {
	@GetMapping(UrlConstants.GET)
	public String login(@RequestParam(required = false) String error, ModelMap modelMap) {
		String message = ObjectConstants.EMPTY_STRING;
		modelMap.addAttribute(ObjectConstants.LOGIN_ITEM, new LoginHomeDto());
		if (error != null && !error.isEmpty()) {
			message = ObjectConstants.LOGIN_FAILURE;
		}
		 modelMap.addAttribute(ObjectConstants.MESSAGE, message);		
		 return UrlConstants.LOGIN_PAGE;
	}
	@GetMapping(UrlConstants.ERROR_NOT_FOUND_URL)
	public String error403() {
		
		 return UrlConstants.ERROR_NOT_FOUND_PAGE;
	}
	

}
