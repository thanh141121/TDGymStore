package net.gymsrote.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.gymsrote.config.login.UserDetailsImpl;
import net.gymsrote.controller.payload.request.CreateCartDetailRequest;
import net.gymsrote.entity.user.User;
import net.gymsrote.service.UserCartDetailService;

@RestController
@RequestMapping("/api/user/cart")
public class UserCartDetailController {
	
	@Autowired 
	UserCartDetailService userCartDetailService;
	
	@GetMapping
	public ResponseEntity<?> findAllByUserId(@AuthenticationPrincipal UserDetailsImpl<User> user){
		return ResponseEntity.ok(userCartDetailService.getAllByIdBuyer(user.getUser().getId()));
	}
	
	@GetMapping("/count")
	public ResponseEntity<?> countItemInCart(@AuthenticationPrincipal UserDetailsImpl<User> user){
		return ResponseEntity.ok(userCartDetailService.countByUser(user.getUser().getId()));
				//(user.getUser().getId()));
	}
	
	@PostMapping("/{idProductVariation}")	
	public ResponseEntity<?> create(@AuthenticationPrincipal UserDetailsImpl<User> user,
			@PathVariable Long idProductVariation, 
			@RequestBody @Valid CreateCartDetailRequest body){
		return ResponseEntity.ok(userCartDetailService.create(idProductVariation,
				user.getUser().getId(), 
				body
				));
	}
	@PatchMapping("/{idProductVariation}")	
	public ResponseEntity<?> changeQuantity(@AuthenticationPrincipal UserDetailsImpl<User> user,
			@PathVariable Long idProductVariation, 
			@RequestBody @Valid CreateCartDetailRequest body){
		return ResponseEntity.ok(userCartDetailService.changeQuantity(idProductVariation,
				user.getUser().getId(), 
				body
				));
	}

	@DeleteMapping("/{idProductVariation}")	
	public ResponseEntity<?> delete(@AuthenticationPrincipal UserDetailsImpl<User> user,
			@PathVariable Long idProductVariation){
		return ResponseEntity.ok(userCartDetailService.delete(idProductVariation,
				user.getUser().getId()
				));
	}
}
