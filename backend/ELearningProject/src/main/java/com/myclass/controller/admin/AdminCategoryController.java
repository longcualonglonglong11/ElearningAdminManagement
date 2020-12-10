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
import com.myclass.dto.CategoryDto;
import com.myclass.service.CategoryService;
@Controller
@RequestMapping(UrlConstants.ADMIN_CATEGORY)
public class AdminCategoryController {
	@Autowired
	private CategoryService categoryService;

	@GetMapping(UrlConstants.GET)
	public String index(ModelMap modelMap) {
		modelMap.addAttribute(ObjectConstants.CATEGORY_LIST, categoryService.findAllDto());
		return UrlConstants.CATEGORY_LIST;
	}

	@GetMapping(UrlConstants.ADD)
	public String add(ModelMap modelMap) {
		modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM, new CategoryDto());
		return UrlConstants.CATEGORY_ADD;
	}

	@PostMapping(UrlConstants.ADD)
	public String add(ModelMap modelMap, @Valid @ModelAttribute(ObjectConstants.CATEGORY_ITEM) CategoryDto dto, BindingResult error) {
		if (error.hasErrors()) {
			return UrlConstants.CATEGORY_ADD;
		}
		if (categoryService.addDto(dto))
			return UrlConstants.CATEGORY_REDIRECT;
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_INSERT);
		return UrlConstants.CATEGORY_ADD;
	}

	@GetMapping(UrlConstants.EDIT_ID)
	public String edit(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		CategoryDto dto = categoryService.findDtoById(id);
		if (dto != null) {
			modelMap.addAttribute(ObjectConstants.CATEGORY_ITEM, dto);
			return UrlConstants.CATEGORY_EDIT;
		}
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.CATEGORY_NOT_EXISTS);
		return UrlConstants.CATEGORY_REDIRECT;

	}

	@PostMapping(UrlConstants.EDIT)
	public String edit(Model modelMap, @Valid @ModelAttribute(ObjectConstants.CATEGORY_ITEM) CategoryDto dto, BindingResult error) {
		if (error.hasErrors()) {
			return UrlConstants.CATEGORY_EDIT;
		}
		if (categoryService.updateDto(dto)) {
			return UrlConstants.CATEGORY_REDIRECT;
		}
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_UPDATE);
		return UrlConstants.CATEGORY_EDIT;

	}

	@GetMapping(UrlConstants.DELETE_ID)
	public String delete(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		if (!categoryService.deleteById(id))
			modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_DELETE);
		return UrlConstants.CATEGORY_REDIRECT;

	}

}
