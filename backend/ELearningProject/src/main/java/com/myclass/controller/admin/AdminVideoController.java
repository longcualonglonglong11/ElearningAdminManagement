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
import org.springframework.web.bind.annotation.RequestMethod;

import com.myclass.constant.ObjectConstants;
import com.myclass.constant.UrlConstants;
import com.myclass.dto.VideoDto;
import com.myclass.service.CategoryService;
import com.myclass.service.CourseService;
import com.myclass.service.VideoService;

@Controller
@RequestMapping(UrlConstants.ADMIN_VIDEO)
public class AdminVideoController {
	@Autowired
	VideoService videoService;
	@Autowired
	CourseService courseService;
	@Autowired
	CategoryService categoryService;
	@RequestMapping(value = UrlConstants.GET, method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		
		modelMap.addAttribute(ObjectConstants.VIDEO_LIST, videoService.findAllDto());
		return UrlConstants.VIDEO_LIST;
	}
	@GetMapping(UrlConstants.ADD)
	public String add(ModelMap modelMap) {
		modelMap.addAttribute(ObjectConstants.COURSE_LIST, courseService.findAllDto());
		modelMap.addAttribute(ObjectConstants.VIDEO_ITEM, new VideoDto());
		return UrlConstants.VIDEO_ADD;
	}
	@PostMapping(UrlConstants.ADD)
	public String add(ModelMap modelMap, @Valid @ModelAttribute(ObjectConstants.VIDEO_ITEM) VideoDto dto, BindingResult error) {
		if (error.hasErrors()) {
			modelMap.addAttribute(ObjectConstants.COURSE_LIST, courseService.findAllDto());
			return UrlConstants.VIDEO_ADD;
		}
	
		if (videoService.addDto(dto))
			return UrlConstants.VIDEO_REDIRECT;
		modelMap.addAttribute(ObjectConstants.COURSE_LIST, courseService.findAllDto());
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_INSERT);
		return UrlConstants.VIDEO_ADD;
	}
	@GetMapping(UrlConstants.EDIT_ID)
	public String edit(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		VideoDto video = videoService.findDtoById(id);
		if (video != null) {
			modelMap.addAttribute(ObjectConstants.VIDEO_ITEM, video);
			modelMap.addAttribute(ObjectConstants.COURSE_LIST, courseService.findAllDto());
			return UrlConstants.VIDEO_EDIT;
		}
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.VIDEO_NOT_EXISTS);
		return UrlConstants.VIDEO_REDIRECT;

	}
	@PostMapping(UrlConstants.EDIT)
	public String edit(Model modelMap, @Valid @ModelAttribute(ObjectConstants.VIDEO_ITEM) VideoDto dto, BindingResult error) {
		if (error.hasErrors()) {
			modelMap.addAttribute(ObjectConstants.COURSE_LIST, courseService.findAllDto());
			return UrlConstants.VIDEO_EDIT;
		}
		if (videoService.updateDto(dto)) {
			return UrlConstants.VIDEO_REDIRECT;
		}
		modelMap.addAttribute(ObjectConstants.COURSE_LIST, courseService.findAllDto());
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_UPDATE);
		return UrlConstants.VIDEO_EDIT;

	}

	@GetMapping(UrlConstants.DELETE_ID)
	public String delete(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		if (!videoService.deleteById(id))
			modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_DELETE);
		return UrlConstants.VIDEO_REDIRECT;

	}
	
}

