package com.myclass.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.myclass.api.controller")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	UserDetailsService userDetailsService;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
	
		.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();
		http.csrf()
		.disable()
		
		.antMatcher("/api/**")
		
		.authorizeRequests()
		.antMatchers("/api/course", "/api/category")
		.hasAnyRole("USER", "ADMIN")
		.antMatchers("/api/role/**", "/api/category/**", "/api/user/**", "/api/target/**", "/api/video/**", "/api/course/**")
		.hasAnyRole("ADMIN")
		.antMatchers("/api/lecturer/course", "/api/lecturer/course/","/api/lecturer/course/add", "/api/lecturer/course/edit", "/api/lecturer/course/delete", "/api/lecturer/course/member/authorize", "/api/lecturer/course/member/cancel", "/api/lecturer/course/member/delete","/api/lecturer/course/member/add")
		.hasAnyRole("LECTURER")
		.antMatchers("/api/lecturer/course/video/add", "/api/lecturer/course/video/edit", "/api/lecturer/course/video/delete")
		.hasAnyRole("LECTURER")
		.antMatchers("/api/lecturer/course/target/add", "/api/lecturer/course/target/edit", "/api/lecturer/course/target/delete")
		.hasAnyRole("LECTURER")
		.antMatchers("/api/lecturer/course/video", "/api/lecturer/course/target", "/api/lecturer/course/member")
		.hasAnyRole("SUB_LECTURER", "LECTURER")

		.anyRequest()
		.authenticated();
		
		http.addFilter(new JwtFilter(authenticationManager(), userDetailsService));
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		.antMatchers("/v2/api-docs",
		"/configuration/ui",
		"/swagger-resources/**",
		"/configuration/security",
		"/swagger-ui.html",
		"/webjars/**");
	}
	
}
