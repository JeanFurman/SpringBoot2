package com.springboot2.study.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.springboot2.study.service.StudyUserDetailsService;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{
	
	private final StudyUserDetailsService studyUserDetailsService;
	
	public SecurityConfig(final StudyUserDetailsService studyUserDetailsService) {
		this.studyUserDetailsService = studyUserDetailsService;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests((authz) -> 
				authz.requestMatchers("/animes/admin/**").hasRole("ADMIN").anyRequest().authenticated())
				.csrf((csrf) -> csrf.disable())
				.formLogin(withDefaults())
				.httpBasic(withDefaults());
		return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception{
		return auth.getAuthenticationManager();
	}
	
//	@Bean
//	public InMemoryUserDetailsManager userDetailsService(){
//		
//		PasswordEncoder encoder = passwordEncoder();
//		UserDetails admin = User.builder()
//				.username("jean")
//				.password(encoder.encode("senha"))
//				.roles("USER", "ADMIN")
//				.build();
//		
//		UserDetails user = User.builder()
//				.username("user")
//				.password(encoder.encode("senha"))
//				.roles("USER")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user, admin);
//		
//	}
//	
}
