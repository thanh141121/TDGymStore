package net.gymsrote.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//import io.swagger.annotations.ApiModelProperty.AccessMode;
import lombok.RequiredArgsConstructor;
import net.gymsrote.entity.EnumEntity.EUserRole;

//import net.gymsrote.security.CustomUserDetailsService;
//import net.gymsrote.security.JwtAuthenticationEntryPoint;
//import net.gymsrote.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	CustomAuthenticationEntryPoint authenticationExceptionHandling;
	
	@Autowired
	CustomAccessDeniedHandler customAccessDeniedHandler;
	
	@Bean
    protected BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    	return authenticationConfiguration.getAuthenticationManager();
    }    
    
    @Bean
    protected AuthTokenFilter authTokenFilter() {
		return new AuthTokenFilter();
	}

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http
    		.cors()
    		.and()
	    	.csrf().disable()
	    	.exceptionHandling().authenticationEntryPoint(authenticationExceptionHandling)
	    	.and()
	    	.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);
    	
    	http
	    	.authorizeRequests()
			.antMatchers("/api/admin/login",
						"/api/user/login", "/api/user/signup").permitAll()
			.and()
    		.authorizeRequests()
    		.antMatchers("/api/user/**").hasRole("USER")
			.and()
			.authorizeRequests()
    		.antMatchers("/api/admin/**").hasRole("ADMIN")
    		.and()
    		.authorizeRequests()
    		.antMatchers("/**").permitAll();
			// .and()
			// .failureHandler(authenticationFailureHandler())

    	http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    	return http.build();
    }

	@Bean
    protected CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}