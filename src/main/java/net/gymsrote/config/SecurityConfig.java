package net.gymsrote.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.swagger.annotations.ApiModelProperty.AccessMode;
import lombok.RequiredArgsConstructor;
import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.filter.CustomAuthenticationFilter;
import net.gymsrote.filter.CustomAuthorizationFilter;

//import net.gymsrote.security.CustomUserDetailsService;
//import net.gymsrote.security.JwtAuthenticationEntryPoint;
//import net.gymsrote.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder bCryptPasswordEncoder()
	{
	    return new BCryptPasswordEncoder();
	}

//	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
		customAuthenticationFilter.setFilterProcessesUrl("/api/login");
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
//		http 
//			.authorizeRequests()
//			.antMatchers("/api/admin/login",
//					"/api/user/login", "/api/user/signup").permitAll()
//			.and()
//			.authorizeRequests()
//			.antMatchers("/api/admin/**").hasAuthority(EUserRole.ROLE_ADMIN.name())
//			.and()
//			.authorizeRequests()
//			.antMatchers("/api/user/**").hasAuthority(EUserRole.ROLE_USER.name())
//			.and()
//    		.authorizeRequests()
//    		.antMatchers("/**").permitAll();
			
		http.authorizeHttpRequests().antMatchers("/api/login/**", "/api/token/refresh").permitAll();
		http.authorizeHttpRequests().antMatchers(HttpMethod.GET, "/api/user/**").hasAuthority("ROLE_USER");
		http.authorizeHttpRequests().antMatchers(HttpMethod.POST, "/api/user/save/**").hasAuthority(EUserRole.ROLE_ADMIN.name());
		http.authorizeHttpRequests().anyRequest().authenticated();
		http.addFilter(customAuthenticationFilter);
		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}