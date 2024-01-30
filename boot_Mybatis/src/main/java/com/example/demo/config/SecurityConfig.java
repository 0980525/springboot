package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.security.CustomUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	//(springSecurity 5 버전 이후 변경)
	//1. passwordEncoder => createDelegatingPasswordEncoder 
	//2. SecurityFilterChain 객체로 설정
	//3. AuthenticationManager 객체로 설정
	
	//createDelegatingPasswordEncoder
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	//SecurityFilterChain
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf->csrf.disable())
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/index","/","/js/**","/dist/**","/board/list","/member/login",
								"/member/register","/comment/**","/upload/**").permitAll()
						.requestMatchers("/member/list").hasAnyRole("ADMIN")
						.anyRequest().authenticated())
				.formLogin(login ->login
						.usernameParameter("email")
						.passwordParameter("pwd")
						.loginPage("/member/login")
						.defaultSuccessUrl("/board/list").permitAll())
				.logout(logout->logout
						.logoutUrl("/member/logout")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID")
						.logoutSuccessUrl("/")).build();
		//return http.build(); 	
	}
	
	//AuthenticationManager
	@Bean
	AuthenticationManager authenticationManager(
			AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	//ueseDetailsService
	@Bean
	UserDetailsService userDetailsService() {
		return new CustomUserService();
	}
	
}
