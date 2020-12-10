package com.myclass.api.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.constant.ObjectConstants;
import com.myclass.constant.UrlConstants;
import com.myclass.dto.UserDto;
import com.myclass.service.UserService;

@RestController
@RequestMapping(UrlConstants.API_ADMIN_USER)
public class ApiUserController {
	@Autowired

	private UserService userService;

	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@GetMapping(UrlConstants.GET)
	public Object get() {

		return new ResponseEntity(userService.findAllDto(), HttpStatus.OK);
	}

	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@GetMapping(UrlConstants.API_FIND_ID)
	public Object get(@PathVariable(UrlConstants.ID) int id) {
		try {
			return new ResponseEntity(userService.findDtoById(id), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity(ObjectConstants.NOT_FOUND, HttpStatus.NOT_FOUND);
		}
	}
	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@PostMapping(UrlConstants.API_ADD)
	public Object post(@RequestBody UserDto user) {
		try {
			String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
			user.setPassword(hashed);
			if (userService.addDto(user))
				return new ResponseEntity(ObjectConstants.API_ADD_SUCCESS, HttpStatus.OK);
			return new ResponseEntity(ObjectConstants.API_ADD_FAILURE, HttpStatus.BAD_GATEWAY);
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity(ObjectConstants.API_ADD_FAILURE, HttpStatus.BAD_GATEWAY);
		}
	}

	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@PutMapping(UrlConstants.API_EDIT)
	public Object put(@RequestBody UserDto user) {
		try {
			String hashed = user.getPassword();
			if (!user.getPassword().startsWith(ObjectConstants.HASHED_KEY))
				hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(ObjectConstants.HASHED_NUMBER));
			user.setPassword(hashed);
			if (userService.updateDto(user))
				return new ResponseEntity(ObjectConstants.API_EDIT_SUCCESS, HttpStatus.OK);
			return new ResponseEntity(ObjectConstants.API_EDIT_FAILURE, HttpStatus.BAD_GATEWAY);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(ObjectConstants.API_EDIT_FAILURE, HttpStatus.BAD_GATEWAY);
		}
	}

	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@DeleteMapping(UrlConstants.API_FIND_ID)
	public Object put(@PathVariable(UrlConstants.ID) int id) {
		try {
			userService.deleteById(id);
			return new ResponseEntity(ObjectConstants.API_DELETE_SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(ObjectConstants.API_DELETE_FAILURE, HttpStatus.BAD_GATEWAY);
		}
	}
}
