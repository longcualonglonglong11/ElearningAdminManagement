package com.myclass.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
import com.myclass.dto.UserDto;
import com.myclass.service.CloudinaryService;
import com.myclass.service.RoleService;
import com.myclass.service.UserService;

@Controller
@RequestMapping(UrlConstants.ADMIN_USER)
public class AdminUserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	CloudinaryService cloudinaryService;

	@GetMapping(UrlConstants.GET)
	public String index(ModelMap modelMap) {
		modelMap.addAttribute(ObjectConstants.USER_LIST, userService.findAllDto());
		return UrlConstants.USER_LIST;
	}

	@GetMapping(UrlConstants.ADD)
	public String add(ModelMap modelMap) {
		modelMap.addAttribute(ObjectConstants.ROLE_LIST, roleService.findAllDto());
		modelMap.addAttribute(ObjectConstants.USER_ITEM, new UserDto());
		return UrlConstants.USER_ADD;
	}

	@PostMapping(UrlConstants.ADD)
	public String add(ModelMap modelMap, @Valid @ModelAttribute(ObjectConstants.USER_ITEM) UserDto dto, BindingResult error) {

		if (error.hasErrors()) {
			modelMap.addAttribute(ObjectConstants.ROLE_LIST, roleService.findAllDto());
			return UrlConstants.USER_ADD;
		}
		String hashed = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(10));
		dto.setPassword(hashed);
		String oldImage = dto.getAvatar();
		String cloudinaryImgURL = ObjectConstants.EMPTY_STRING;
		if (!cloudinaryService.checkFile(dto.getFileDatas())) {
			cloudinaryImgURL = cloudinaryService.uploadFile(dto.getFileDatas());
		} else {
			cloudinaryImgURL = oldImage;
		}
		dto.setAvatar(cloudinaryImgURL);
		if (userService.addDto(dto)) {
			return UrlConstants.USER_REDIRECT;
		}
		modelMap.addAttribute(ObjectConstants.ROLE_LIST, roleService.findAllDto());
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_INSERT);
		return UrlConstants.USER_ADD;
	}

	@GetMapping(UrlConstants.EDIT_ID)
	public String edit(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		UserDto dto = userService.findDtoById(id);

		if (dto != null) {
			modelMap.addAttribute(ObjectConstants.ROLE_LIST, roleService.findAllDto());
			modelMap.addAttribute(ObjectConstants.USER_ITEM, dto);
			return UrlConstants.USER_EDIT;
		}
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.USER_NOT_EXISTS);
		return UrlConstants.USER_REDIRECT;

	}

	@PostMapping(UrlConstants.EDIT)
	public String edit(Model modelMap, @Valid @ModelAttribute(ObjectConstants.USER_ITEM) UserDto dto, BindingResult error) {
		if (error.hasErrors()) {
			modelMap.addAttribute(ObjectConstants.ROLE_LIST, roleService.findAllDto());
			return UrlConstants.USER_EDIT;
		}
		String hashed = dto.getPassword();
		if (!dto.getPassword().startsWith(ObjectConstants.HASHED_KEY))
			hashed = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(10));
		dto.setPassword(hashed);
		String oldImage = dto.getAvatar();
		String cloudinaryImgURL = ObjectConstants.EMPTY_STRING;
		if (!cloudinaryService.checkFile(dto.getFileDatas())) {
			cloudinaryImgURL = cloudinaryService.uploadFile(dto.getFileDatas());
		} else {
			cloudinaryImgURL = oldImage;
		}
		dto.setAvatar(cloudinaryImgURL);
		if (userService.updateDto(dto)) {
			return UrlConstants.USER_REDIRECT;
		}
		modelMap.addAttribute(ObjectConstants.ROLE_LIST, roleService.findAllDto());
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_UPDATE);
		return UrlConstants.USER_EDIT;

	}

	@GetMapping(UrlConstants.DELETE_ID)
	public String delete(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		if (!userService.deleteById(id))
			modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_DELETE);
		return UrlConstants.USER_REDIRECT;

	}
}
