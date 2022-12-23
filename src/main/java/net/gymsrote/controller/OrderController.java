package net.gymsrote.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import net.gymsrote.config.login.UserDetailsImpl;
import net.gymsrote.controller.payload.request.order.CreateOrderRequest;
import net.gymsrote.entity.user.User;
import net.gymsrote.service.OrderService;

@RestController
@RequestMapping("/api/user/order")
public class OrderController {
	@Autowired
	OrderService orderService;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(
			@AuthenticationPrincipal UserDetailsImpl<User> user,
			@RequestBody @Valid CreateOrderRequest body
			){
		return ResponseEntity.ok(orderService.create(user.getUser().getId(), body));
	}
}
