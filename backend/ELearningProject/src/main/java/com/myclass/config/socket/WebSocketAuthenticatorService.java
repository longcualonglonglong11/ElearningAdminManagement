package com.myclass.config.socket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import com.myclass.dto.UserDto;
import com.myclass.service.UserService;

@Component
public class WebSocketAuthenticatorService {
	@Autowired
	private UserService userService;
	private final String ERROR_EMAIL_NOT_VALID =  "Username was null or empty.";
	private final String ERROR_ACCOUNT_NOT_VALID =  "Account does not exist.";
	// This method MUST return a UsernamePasswordAuthenticationToken instance, the
	// spring security chain is testing it with 'instanceof' later on. So don't use
	// a subclass of it or any other class
	public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String email) {
		if (email == null || email.trim().isEmpty()) {
			throw new AuthenticationCredentialsNotFoundException(ERROR_EMAIL_NOT_VALID);
		}

		UserDto account = userService.findDtoByEmail(email);
		if (account == null)
			throw new AuthenticationCredentialsNotFoundException(ERROR_ACCOUNT_NOT_VALID);

		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(account.getRoleName());
		return new UsernamePasswordAuthenticationToken(email, null, authorities);

		//ANOTHER WAY
	//Collections.singleton((GrantedAuthority) () -> "ROLE_ADMIN") // MUST provide at least one role
	}
}