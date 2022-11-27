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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import net.gymsrote.controller.payload.response.ListResponse;
import net.gymsrote.dto.UserDTO;
import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.entity.user.UserRole;
import net.gymsrote.service.UserService;
import net.gymsrote.entity.user.User;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping("/addresses/{id}")
	public ResponseEntity<?> getAddresses(@PathVariable("id") Long id){
		System.out.println("get User Addresses");
		return ResponseEntity.ok().body(userService.getListAddress(id));
	}
	
	@GetMapping("/users")
	//@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getUsers(){
		System.out.println("get Users");
		return ResponseEntity.ok().body(userService.getUsers());
	}
	
	@PatchMapping("/enabled/{id}")
	public ResponseEntity<?> isEnabled(@PathVariable("id") Long id){
		return ResponseEntity.ok(userService.isEnabled(id, true));
	}
	
/*	@GetMapping("/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException{
	}
	
	@PostMapping("/user/save")
	public ResponseEntity<User> saveUser(@RequestBody User user){
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toString());
		return ResponseEntity.created(uri).body(userService.saveUser(user));
	}
	
	@PostMapping("/admin/role/save")
	public ResponseEntity<UserRole> saveRole(@RequestBody UserRole role){
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/admin/role/save").toString());
		return ResponseEntity.created(uri).body(userService.saveRole(role));
	}
	@PostMapping("/admin/role/addToUser")
	public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){
		userService.addRoleToUser(form.getUsername(), form.getRolename());
		return ResponseEntity.ok().build();
	}
	
*/
	
}

@Data
class RoleToUserForm {
	private String username;
	private EUserRole rolename;
}
