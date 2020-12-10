package com.myclass.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.myclass.constant.ObjectConstants;

import io.jsonwebtoken.Jwts;

public class JwtFilter extends BasicAuthenticationFilter {
	@Autowired
	private UserDetailsService userDetailsService;

	public JwtFilter(AuthenticationManager authentication, UserDetailsService userDetailsService) {
		super(authentication);
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String tokenHeader = request.getHeader(ObjectConstants.AUTHORIZATION_HEADER);

		System.out.println(tokenHeader);
		// chain.doFilter(request, response);
		if (tokenHeader != null && tokenHeader.startsWith(ObjectConstants.HEADER_VALID)) {
			try {
				String token = tokenHeader.replace(ObjectConstants.HEADER_VALID, ObjectConstants.EMPTY_STRING);
				String email = Jwts.parser().setSigningKey(ObjectConstants.API_KEY).parseClaimsJws(token).getBody().getSubject();
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
				Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				chain.doFilter(request, response);

			} catch (Exception e) {
				System.out.println(ObjectConstants.NOT_VALID_TOKEN);
				e.printStackTrace();
			}


		}


	}

}
