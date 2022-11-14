package net.gymsrote.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class CustomAuthorizationFilter {//extends OncePerRequestFilter {
//
//	private static final String AUTHORIZATION = "Authorization";
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		if(request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/token/refresh")) {
//			filterChain.doFilter(request, response);
//		}else {
//			String authorizationHeader = request.getHeader(AUTHORIZATION);
//			if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//				try {
//					String token = authorizationHeader.substring("Bearer ".length());
//					Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
//					JWTVerifier verifier = JWT.require(algorithm).build();
//					DecodedJWT decodedJWT = verifier.verify(token);
//					String username = decodedJWT.getSubject();
//					List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
//					Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//					roles.stream().forEach(role -> {
//						authorities.add(new SimpleGrantedAuthority(role));
//					});
//					UsernamePasswordAuthenticationToken authenticationToken = 
//							new UsernamePasswordAuthenticationToken(username, null, authorities);
//					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//					filterChain.doFilter(request, response);
//				}catch(Exception e) {
//					log.error("Error logging in: {}", e.getMessage());
//					response.setHeader("error", e.getMessage());
//					response.setStatus(HttpStatus.FORBIDDEN.value());
////					response.sendError(HttpStatus.FORBIDDEN.value());
//					Map<String, String> error = new HashMap<>();
//					error.put("error_massage", e.getMessage());
//					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//					new ObjectMapper().writeValue(response.getOutputStream(), error);
//				}
//			}else {
//				filterChain.doFilter(request, response);
//			}
//		}
//	
//	}
//
}
