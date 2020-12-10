package com.myclass.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myclass.constant.UrlConstants;

@Controller
@RequestMapping(value = {  UrlConstants.HOME_ADMIN_ONE, UrlConstants.HOME_ADMIN_SECOND })
public class AdminHomeController {
	@RequestMapping(value = UrlConstants.GET, method = RequestMethod.GET)
	public String index() {
		return UrlConstants.HOMEPAGE_ADMIN;
	}
}