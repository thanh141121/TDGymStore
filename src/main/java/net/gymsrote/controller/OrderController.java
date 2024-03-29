package net.gymsrote.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.gymsrote.config.login.UserDetailsImpl;
import net.gymsrote.controller.payload.request.PageInfoRequest;
import net.gymsrote.controller.payload.request.order.CreateOrderRequest;
import net.gymsrote.entity.EnumEntity.EOrderStatus;
import net.gymsrote.entity.user.User;
import net.gymsrote.service.OrderService;
import net.gymsrote.service.PaymentService;
import net.gymsrote.utility.PagingInfo;

@RestController
@RequestMapping("/api/user/order")
public class OrderController {
	@Autowired
	OrderService orderService;

	@Autowired
	PaymentService paymentService;

	@GetMapping
	public ResponseEntity<?> getByStatus(
			@RequestParam EOrderStatus status,
			@AuthenticationPrincipal UserDetailsImpl<User> user,
			@RequestParam(required = false) Integer page,
			@RequestBody(required = false) PageInfoRequest infoRequest) {

		if (infoRequest == null)
			infoRequest = new PageInfoRequest();
		if (page != null)
			infoRequest.setCurrentPage(page);
		Pageable pageable = PageRequest.of(infoRequest.getCurrentPage(), infoRequest.getSize(),
				Sort.by(Sort.Direction.DESC, "id"));

		return ResponseEntity.ok(
				orderService.getByStatus(
						user.getUser(),
						status,
						pageable));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(
			HttpServletRequest req,
			@AuthenticationPrincipal UserDetailsImpl<User> user,
			@RequestBody @Valid CreateOrderRequest body) {
		return ResponseEntity.ok(orderService.create(user.getUser().getId(), body, req));
	}

	@PostMapping(value = "/{id}")
	public ResponseEntity<?> createPayment(
			@PathVariable Long id,
			HttpServletRequest req,
			@AuthenticationPrincipal UserDetailsImpl<User> user) {
		return ResponseEntity.ok(paymentService.createPayment(id, user.getUser().getId(), req));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateOrderStatus(@PathVariable Long id,
			@AuthenticationPrincipal UserDetailsImpl<User> user,
			@RequestParam("new-status") EOrderStatus newStatus) {
		return ResponseEntity.ok(orderService.updateStatus(id, user.getUser(), newStatus));
	}
}
