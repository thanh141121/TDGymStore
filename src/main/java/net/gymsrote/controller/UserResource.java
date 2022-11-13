package net.gymsrote.controller;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.entity.user.RoleEntity;
import net.gymsrote.entity.user.UserEntity;
import net.gymsrote.entity.user.UserRoleEntity;
import net.gymsrote.service.impl.UserService;

@RestController
@RequestMapping("/api")
public class UserResource {
	@Autowired
	UserService userService;
	
	//Change to admin/users
	@GetMapping("/user/users")
	public ResponseEntity<List<UserEntity>> getUsers(){
		return ResponseEntity.ok().body(userService.getUsers());
	}
	
	@GetMapping("/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String authorizationHeader = request.getHeader("Authorization");
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String refreshToken = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refreshToken);
				String username = decodedJWT.getSubject();
				UserEntity user = userService.getUser(username);
				String accessToken = JWT.create()
						.withSubject(user.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles", user.getUserRoles().stream().map(userRole -> userRole.getRoles().getName().name()).collect(Collectors.toList()))
						.sign(algorithm);
				Map<String, String> tokens = new HashMap<>();
				tokens.put("accessToken", accessToken);
				tokens.put("refreshToken", refreshToken);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
				
			}catch(Exception e) {
				response.setStatus(HttpStatus.FORBIDDEN.value());
//				response.sendError(HttpStatus.FORBIDDEN.value());
				Map<String, String> error = new HashMap<>();
				error.put("error_massage", e.getMessage());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
		}else {
			throw new RuntimeException("Refresh Token is missing");
		}
	}
	
	@PostMapping("/user/save")
	public ResponseEntity<UserEntity> saveUser(@RequestBody UserEntity user){
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toString());
		return ResponseEntity.created(uri).body(userService.saveUser(user));
	}
	
	@PostMapping("/admin/role/save")
	public ResponseEntity<RoleEntity> saveRole(@RequestBody RoleEntity role){
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/admin/role/save").toString());
		return ResponseEntity.created(uri).body(userService.saveRole(role));
	}
	@PostMapping("/admin/role/addToUser")
	public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){
		userService.addRoleToUser(form.getUsername(), form.getRolename());
		return ResponseEntity.ok().build();
	}
	

	
}

@Data
class RoleToUserForm {
	private String username;
	private EUserRole rolename;
}
