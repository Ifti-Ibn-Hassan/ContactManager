package com.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider Provider=new DaoAuthenticationProvider();
		
		Provider.setUserDetailsService(userDetailsService);
		Provider.setPasswordEncoder(passwordEncoder);
		
		return Provider;
	}
	
	///configure method
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, DaoAuthenticationProvider authProvider) throws Exception{
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.authenticationProvider(authProvider)
				.build();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.disable())
				.authorizeHttpRequests(auth->auth
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/user/**").hasRole("USER")
						.requestMatchers("/**").permitAll()
				)
				.formLogin().loginPage("/signin")
//				.loginProcessingUrl("/dologin")
				.defaultSuccessUrl("/user/index");
//				.failureUrl("/login-fail");
//				.formLogin(Customizer.withDefaults());
		

		return http.build();
	}
	
}
