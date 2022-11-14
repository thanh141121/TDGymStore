package net.gymsrote.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
// import net.gymsrote.security.CustomUserDetailsService;
// import net.gymsrote.security.JwtAuthenticationEntryPoint;
// import net.gymsrote.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	@Autowired
	CustomAuthenticationEntryPoint authenticationExceptionHandling;

	@Autowired
	CustomAccessDeniedHandler customAccessDeniedHandler;

	@Bean
	protected BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	protected AuthenticationManager authenticationManager(
			AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	protected AuthTokenFilter authTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		http.authorizeRequests()
				.antMatchers("/api/admin/login", "/api/user/login", "/api/user/signup").permitAll()
				.and().authorizeRequests().antMatchers("/api/admin/**").hasRole("ADMIN").and()
				.authorizeRequests().antMatchers("/api/user/**").hasRole("USER").and()
				.authorizeRequests().antMatchers("/**").permitAll();
		http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
