package com.myclass.controller.admin;


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

import com.myclass.constant.ObjectConstants;
import com.myclass.constant.UrlConstants;
import com.myclass.dto.TargetDto;
import com.myclass.service.CourseService;
import com.myclass.service.TargetService;

@Controller
@RequestMapping(UrlConstants.ADMIN_TARGET)
public class AdminTargetController {
	@Autowired
	private TargetService targetService;
	@Autowired
	private CourseService courseServive;

	@GetMapping(UrlConstants.GET)
	public String index(ModelMap modelMap) {
		modelMap.addAttribute(ObjectConstants.TARGET_LIST, targetService.findAllDto());
		return UrlConstants.TARGET_LIST;
	}

	@GetMapping(UrlConstants.ADD)
	public String add(ModelMap modelMap) {
		modelMap.addAttribute(ObjectConstants.TARGET_ITEM, new TargetDto());
		modelMap.addAttribute(ObjectConstants.COURSE_LIST, courseServive.findAllDto());
		return UrlConstants.TARGET_ADD;
	}

	@PostMapping(UrlConstants.ADD)
	public String add(ModelMap modelMap, @Valid @ModelAttribute(ObjectConstants.TARGET_ITEM) TargetDto dto, BindingResult error) {
		if (error.hasErrors()) {
			modelMap.addAttribute(ObjectConstants.COURSE_LIST, courseServive.findAllDto());
			return UrlConstants.TARGET_ADD;
		}
		if (targetService.addDto(dto))
			return UrlConstants.TARGET_REDIRECT;
		modelMap.addAttribute(ObjectConstants.COURSE_LIST, courseServive.findAllDto());
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_INSERT);
		return UrlConstants.TARGET_ADD;
	}

	@GetMapping(UrlConstants.EDIT_ID)
	public String edit(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		TargetDto dto = targetService.findDtoById(id);
		if (dto != null) {
			modelMap.addAttribute(ObjectConstants.TARGET_ITEM, dto);
			modelMap.addAttribute(ObjectConstants.COURSE_LIST, courseServive.findAllDto());
			return UrlConstants.TARGET_EDIT;
		}
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_UPDATE);
		return UrlConstants.TARGET_REDIRECT;

	}

	@PostMapping(UrlConstants.EDIT)
	public String edit(Model modelMap, @Valid @ModelAttribute(ObjectConstants.TARGET_ITEM) TargetDto dto, BindingResult error) {
		if (error.hasErrors()) {
			modelMap.addAttribute(ObjectConstants.COURSE_LIST, courseServive.findAllDto());
			return UrlConstants.TARGET_EDIT;
		}
		if (targetService.updateDto(dto)) {
			return UrlConstants.TARGET_REDIRECT;
		}
		modelMap.addAttribute(ObjectConstants.COURSE_LIST, courseServive.findAllDto());
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_UPDATE);
		return UrlConstants.TARGET_EDIT;

	}

	@GetMapping(UrlConstants.DELETE_ID)
	public String delete(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		if (!targetService.deleteById(id))
			modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_DELETE);
		return UrlConstants.TARGET_REDIRECT;

	}
}
