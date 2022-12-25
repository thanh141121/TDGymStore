package net.gymsrote.controller;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import net.gymsrote.config.login.UserDetailsImpl;
import net.gymsrote.controller.payload.request.order.CreateOrderRequest;
import net.gymsrote.entity.EnumEntity.EOrderStatus;
import net.gymsrote.entity.EnumEntity.EPaymentMethod;
import net.gymsrote.entity.user.User;
import net.gymsrote.service.OrderService;
import net.gymsrote.utility.PagingInfo;



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

	@GetMapping(produces = "application/json")
	public ResponseEntity<?> getBuyers(
		@AuthenticationPrincipal UserDetailsImpl<User> user,
			@RequestParam(required = false) 
				Integer page,
			@RequestParam(required = false) 
				Integer size,
			@RequestParam(required = false) 
			@Min(1) @Max(3)
				Integer sortBy,
			@RequestParam(required = false)
				Boolean sortDescending) {

		return ResponseEntity.ok(
			orderService.getAll(
				user.getUser().getId(),
				new PagingInfo(page, size, sortBy, sortDescending),
				true
			)
		);
	}
}