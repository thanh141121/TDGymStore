package net.gymsrote.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import net.gymsrote.config.login.UserDetailsServiceImpl;
import net.gymsrote.utility.component.JwtUtil;

public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	CustomAuthenticationEntryPoint authenticationExceptionHandling;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@SuppressWarnings("serial")
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = jwtUtil.parseJwt(request);

			if (token != null && !jwtUtil.isTokenExpired(token)) {
				String username = jwtUtil.getUsernameToken(token);

				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			authenticationExceptionHandling.commence(
					request, 
					response, 
					new AuthenticationException(e.getMessage(), e) { }
				);
			return;
		}

		filterChain.doFilter(request, response);
	}
}