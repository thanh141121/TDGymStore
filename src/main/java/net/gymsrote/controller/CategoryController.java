package net.gymsrote.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import net.gymsrote.config.login.UserDetailsImpl;
import net.gymsrote.controller.payload.request.order.CreateOrderRequest;
import net.gymsrote.entity.user.User;
import net.gymsrote.service.OrderService;
import net.gymsrote.service.ProductCategoryService;

@RestController
@RequestMapping("/api/product-category")
public class CategoryController {
	@Autowired
	ProductCategoryService productCategoryService;	
	
	@Autowired
	OrderService orderService;

	
	@GetMapping
	public ResponseEntity<?> getAllCategories(){
		return ResponseEntity.ok(productCategoryService.getAllCategories());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		return ResponseEntity.ok(productCategoryService.get(id));
	}
	
	//Create new Category
}
