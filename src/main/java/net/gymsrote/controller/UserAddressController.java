package net.gymsrote.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.gymsrote.config.login.UserDetailsImpl;
import net.gymsrote.controller.payload.request.CreateAddressRequest;
import net.gymsrote.entity.user.User;
import net.gymsrote.service.AddressService;

@RestController
@RequestMapping("/api/user/address")
public class UserAddressController {
	@Autowired
	private AddressService addressService;
	
	@GetMapping
	public ResponseEntity<?> getAllAddressByUser(@AuthenticationPrincipal UserDetailsImpl<User> user)
	{
		return ResponseEntity.ok(addressService.getAllAddressByUser(user.getUser().getId()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@AuthenticationPrincipal UserDetailsImpl<User> user,
			@PathVariable Long id)
	{
		return ResponseEntity.ok(addressService.get(id));
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid CreateAddressRequest body,
			@AuthenticationPrincipal UserDetailsImpl<User> user) {
		return ResponseEntity.ok(addressService.create(user.getUser().getId(), body, null));
	}
	
	@PutMapping("/{idAddress}")
	public ResponseEntity<?> update(@RequestBody @Valid CreateAddressRequest body,
			@AuthenticationPrincipal UserDetailsImpl<User> user,
			@PathVariable Long idAddress) {
		return ResponseEntity.ok(addressService.create(user.getUser().getId(), body, idAddress));
	}
}
