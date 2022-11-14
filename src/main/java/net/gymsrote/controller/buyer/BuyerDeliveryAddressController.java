package net.gymsrote.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.gymsrote.config.login.UserDetailsImpl;
import net.gymsrote.controller.payload.request.buyerdeliveryaddress.CreateBuyerDeliveryAddressRequest;
import net.gymsrote.controller.payload.request.buyerdeliveryaddress.UpdateBuyerDeliveryAddressRequest;
import net.gymsrote.entity.user.buyer.Buyer;
import net.gymsrote.service.BuyerDeliveryAddressService;

@RestController
@RequestMapping("/api/buyer/delivery-address")
@PreAuthorize("hasAuthority('ACTIVE_BUYER')")
public class BuyerDeliveryAddressController {
	@Autowired
	BuyerDeliveryAddressService buyerDeliveryAddressService;

	@GetMapping
	public ResponseEntity<?> getAll(@AuthenticationPrincipal UserDetailsImpl<Buyer> buyer) {
		return ResponseEntity
				.ok(buyerDeliveryAddressService.getAllByIdBuyer(buyer.getUser().getId(), false));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id,
			@AuthenticationPrincipal UserDetailsImpl<Buyer> buyer) {
		return ResponseEntity.ok(buyerDeliveryAddressService.get(id, buyer.getUser().getId()));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id,
			@RequestBody @Valid UpdateBuyerDeliveryAddressRequest body,
			@AuthenticationPrincipal UserDetailsImpl<Buyer> buyer) {
		return ResponseEntity
				.ok(buyerDeliveryAddressService.update(id, buyer.getUser().getId(), body));
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid CreateBuyerDeliveryAddressRequest body,
			@AuthenticationPrincipal UserDetailsImpl<Buyer> buyer) {
		return ResponseEntity.ok(buyerDeliveryAddressService.create(buyer.getUser().getId(), body));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id,
			@AuthenticationPrincipal UserDetailsImpl<Buyer> buyer) {
		return ResponseEntity.ok(buyerDeliveryAddressService.delete(id, buyer.getUser().getId()));
	}
}
