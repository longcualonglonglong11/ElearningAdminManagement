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
import com.myclass.dto.RoleDto;
import com.myclass.service.RoleService;
@Controller
@RequestMapping(UrlConstants.ADMIN_ROLE)

public class AdminRoleController {
	@Autowired
	private RoleService roleService;
	

	@GetMapping(UrlConstants.GET)
	public String index(ModelMap modelMap) {
		modelMap.addAttribute(ObjectConstants.ROLE_LIST, roleService.findAllDto());
		return UrlConstants.ROLE_LIST;
	}

	@GetMapping(UrlConstants.ADD)
	public String add(ModelMap modelMap) {
		modelMap.addAttribute(ObjectConstants.ROLE_ITEM, new RoleDto());
		return UrlConstants.ROLE_ADD;
	}

	@PostMapping(UrlConstants.ADD)
	public String add(ModelMap modelMap, @Valid @ModelAttribute(ObjectConstants.ROLE_ITEM) RoleDto dto, BindingResult error) {
		if (error.hasErrors()) {
			return UrlConstants.ROLE_ADD;
		}
		if (roleService.addDto(dto))
			return UrlConstants.ROLE_REDIRECT;
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_INSERT);
		return UrlConstants.ROLE_ADD;
	}

	@GetMapping(UrlConstants.EDIT_ID)
	public String edit(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		RoleDto dto = roleService.findDtoById(id);
		if (dto != null) {
			modelMap.addAttribute(ObjectConstants.ROLE_ITEM, dto);
			return UrlConstants.ROLE_EDIT;
		}
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ROLE_NOT_EXISTS);
		return UrlConstants.ROLE_REDIRECT;
	}

	@PostMapping(UrlConstants.EDIT)
	public String edit(Model modelMap, @Valid @ModelAttribute(ObjectConstants.ROLE_ITEM) RoleDto dto, BindingResult error) {
		if (error.hasErrors()) {
			return UrlConstants.ROLE_EDIT;
		}
		if (roleService.updateDto(dto)) {
			return UrlConstants.ROLE_REDIRECT;
		}
		modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_UPDATE);
		return UrlConstants.ROLE_EDIT;

	}

	@GetMapping(UrlConstants.DELETE_ID)
	public String delete(Model modelMap, @PathVariable(UrlConstants.ID) int id) {
		if (!roleService.deleteById(id)) {
			modelMap.addAttribute(ObjectConstants.MESSAGE, ObjectConstants.ERROR_DELETE);
			modelMap.addAttribute(ObjectConstants.ROLE_LIST, roleService.findAllDto());
			return UrlConstants.ROLE_LIST;
		}
		return UrlConstants.ROLE_REDIRECT;
	}
}
