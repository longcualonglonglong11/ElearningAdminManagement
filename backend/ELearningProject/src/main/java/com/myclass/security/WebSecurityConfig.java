package com.myclass.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.myclass.service.impl.AuthenticationSuccessHandlerImpl;

@Configuration
@EnableWebSecurity
@Order(30)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationSuccessHandlerImpl authenticationSuccessHandlerImpl;
    
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http.csrf()
		
		
		.disable()
		.authorizeRequests()
		

		
		
		

		.antMatchers("/authentication/api/**")
		.permitAll()
		.antMatchers("/home", "/login/**", "/register/**", "/swagger-ui.html/**")
		.permitAll()
		.antMatchers("/swagger-resources/**")
		.permitAll()

		.antMatchers("/admin/**")
		.hasAnyRole("ADMIN")
		.antMatchers("/course/detail/**")
		.hasAnyRole("USER")
		.antMatchers("/lecturer/course/**")
		.hasAnyRole("SUB_LECTURER", "LECTURER")
		.antMatchers("/lecturer/**")
		.hasAnyRole("LECTURER")
		.antMatchers("/sublecturer/**")
		.hasAnyRole("SUB_LECTURER")
		
		.anyRequest()
		.authenticated();
		
		http.exceptionHandling().accessDeniedPage("/login/403");
		http.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/login")
		.usernameParameter("email")
		.passwordParameter("password")
		.successHandler(authenticationSuccessHandlerImpl)
		.failureUrl("/login?error=1");
		http.logout()
		.logoutUrl("/logout")
		.logoutSuccessUrl("/home")
		.deleteCookies("JSESSIONID");
		
		
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/assets/**", "/js/**","/css/**","/img/**","/webjars/**");
		//ADD ALLOWED SOCKET
		web.ignoring().antMatchers("/socket/**","/assets/**", "/js/**","/css/**","/img/**","/webjars/**");

	}
}
