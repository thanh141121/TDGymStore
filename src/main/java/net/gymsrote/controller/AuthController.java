package net.gymsrote.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.gymsrote.controller.payload.request.LoginKeyPasswordRequest;
import net.gymsrote.controller.payload.request.SignUpRequest;
import net.gymsrote.controller.payload.response.BaseResponse;
import net.gymsrote.controller.payload.response.LoginResponse;
import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.service.UserService;
import net.gymsrote.service.authen.AuthService;
//@RestController
@Controller
@RequestMapping("/api")
public class AuthController {
	@Autowired
	AuthService authService;

	@Autowired
	UserService userService;
	
//	@Operation(
//			summary = "Login API for admin",
//			description = "Login key can be username, phone number or email that user is registered.")
//	@ApiResponses(value = {
//			@ApiResponse(responseCode = "200",
//					description = "Successful",
//					content = { @Content(mediaType = "application/json") })
//	})
//	@PostMapping("/admin/login")
//	public ResponseEntity<?> admin(@RequestBody LoginKeyPasswordRequest body) {
//		return ResponseEntity.ok(login(body, EUserRole.ADMIN));
//	}
	
	@Operation(
			summary = "Login API for buyer",
			description = "Login key can be username, phone number or email that user is registered.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Successful",
					content = { @Content(mediaType = "application/json") })
	})

	@ResponseBody
	@PostMapping("login")
	public ResponseEntity<?> buyer(@RequestBody LoginKeyPasswordRequest body) {
		return ResponseEntity.ok(login(body));
	}
	
	private LoginResponse<?> login(LoginKeyPasswordRequest body) {
		return authService.authenticateWithUsernamePassword(body.getLoginKey(), body.getPassword());
	}

//	@PostMapping("signup")
//	public ResponseEntity<?> signup(@RequestPart SignUpRequest body, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
//		return ResponseEntity.ok(authService.register(body, getSiteURL(request)));
//	}
	@PostMapping("signup")
	public String signup(@Valid @RequestBody SignUpRequest body, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		BaseResponse temp =  authService.register(body, getSiteURL(request));
		return "register_success";
	}
	private String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}	
	
	@GetMapping("/verify")
	public String verifyUser(@Param("code") String code) {
		if (authService.verify(code)) {
			return "verify_success";
		} else {
			return "verify_fail";
		}
	}
}