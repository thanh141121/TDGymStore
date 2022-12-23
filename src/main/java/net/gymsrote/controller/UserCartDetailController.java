package net.gymsrote.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
	
	@GetMapping
	public ResponseEntity<?> countItemInCart(@AuthenticationPrincipal UserDetailsImpl<User> user){
		return ResponseEntity.ok(userCartDetailService.
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
//	@Autowired
//	UserService userService;

/*	@RequestMapping(method = RequestMethod.GET, value = "/api/javainuse")
	public String sayHello() {
		return "Swagger Hello World";
	}*/
	//
	// @GetMapping(value = "/new")
	// public NewOutput showNew(@RequestParam("page") int page,
	// @RequestParam("limit") int limit) {
	// NewOutput result = new NewOutput();
	// result.setPage(page);
	// Pageable pageable = PageRequest.of(page -1, limit);
	// result.setListResult(newService.findAll(pageable));
	// result.setTotalPage((int) Math.ceil((double) (newService.totalItem()) / limit));
	// return result;
	// }
	//
	// @PostMapping(value = "/new")
	// public NewDTO createNew(@RequestBody NewDTO model) {
	// return newService.save(model);
	// }
	//
	// @PutMapping(value="/new/{id}")
	// public NewDTO updateNew(@RequestBody NewDTO model, @PathVariable("id") long id) {
	// model.setId(id);
	// return newService.save(model);
	//
	// }
	//
	// @DeleteMapping(value = "/new")
	// public void deleteNew(@RequestBody long[] ids) {
	// newService.delete(ids);
	// }
}
