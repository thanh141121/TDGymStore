package net.gymsrote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.gymsrote.config.login.UserDetailsImpl;
import net.gymsrote.controller.payload.request.PageInfoRequest;
import net.gymsrote.entity.user.User;
import net.gymsrote.service.ProductService;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
	@Autowired
	ProductService productService;
	
	@GetMapping
	public ResponseEntity<?> getAllProducts(@RequestParam(value = "page", required=false) Integer page, @RequestBody(required=false) PageInfoRequest infoRequest){
		if(infoRequest == null) infoRequest = new PageInfoRequest();
		if(page != null) infoRequest.setCurrentPage(page);
		Pageable pageable = PageRequest.of(infoRequest.getCurrentPage(), infoRequest.getSize(), infoRequest.buildSort());
		return ResponseEntity.ok(productService.getAllProducts(pageable));
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> searchProducts(@RequestParam String key){
		PageInfoRequest request = new PageInfoRequest();
		Pageable pageable = PageRequest.of(request.getCurrentPage(), request.getSize());
		return ResponseEntity.ok(productService.search(key,pageable));
//		return ResponseEntity.ok(productService.getAllProducts());
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getDetails(@PathVariable("id") long id, @AuthenticationPrincipal UserDetailsImpl<User> user) {
		return ResponseEntity.ok(productService.getById(id, user));
	}
}