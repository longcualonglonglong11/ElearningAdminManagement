package com.myclass.api.controller.login;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.constant.ObjectConstants;
import com.myclass.constant.UrlConstants;
import com.myclass.dto.ApiLoginDto;
import com.myclass.dto.LoginDto;
import com.myclass.service.UserInformationService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
//@RequestMapping(UrlConstants.API_AUTHENTICATION)
@RequestMapping("api/authentication")

public class ApiAuthController {
	@Autowired
	UserInformationService userInformationService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@SuppressWarnings({ ObjectConstants.RAW_TYPE, ObjectConstants.UNCHECKED_TYPE })
	@GetMapping(UrlConstants.GET)

	public Object get() {
		LoginDto account = userInformationService.getUserInformation();
		System.out.println(account.getRoleName());
		String introduce = ObjectConstants.INFORMATION_1 + account.getEmail() + ObjectConstants.INFORMATION_2
				+ account.getFullname() + ObjectConstants.INFORMATION_3 + account.getRoleDescription();
		String privilege = ObjectConstants.PRIVLEGE;
		if (account.getRoleName().equals(ObjectConstants.ADMIN)) {

			privilege = privilege + ObjectConstants.DETAIL_PRIVLEGE_ADMIN;

		} else if (account.getRoleName().equals(ObjectConstants.TEACHER)) {

			privilege = privilege + ObjectConstants.DETAIL_PRIVLEGE_TEACHER;

		} else if (account.getRoleName().equals(ObjectConstants.TEACHING_ASSISTANT)) {

			privilege = privilege + ObjectConstants.DETAIL_PRIVLEGE_TEACHING_ASSISTANT;

		} else
			privilege = privilege + ObjectConstants.DETAIL_PRIVLEGE_USER;
		return new ResponseEntity(introduce + privilege, HttpStatus.OK);

	}

	@PostMapping(UrlConstants.GET)
	public Object login(@RequestBody ApiLoginDto dto) {
		try {
			Authentication authentication = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
			authentication = authenticationManager.authenticate(authentication);
			Date now = new Date();
			String token = Jwts
					.builder()
					.setSubject(dto.getEmail())
					.setIssuedAt(now)
					.setExpiration(new Date(now.getTime() + 8640000000L))
					.signWith(SignatureAlgorithm.HS512, ObjectConstants.API_KEY)
					.compact();
			SecurityContextHolder.getContext().setAuthentication(authentication);
			if (token != null)
				return new ResponseEntity<String>(token, HttpStatus.OK);
			else
				return new ResponseEntity<String>(ObjectConstants.TOKEN_NO_VERIFICATION, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(ObjectConstants.AUTHENTICATION_FAILURE, HttpStatus.BAD_REQUEST);

		}
	}

}
