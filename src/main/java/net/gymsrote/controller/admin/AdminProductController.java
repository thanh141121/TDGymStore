package net.gymsrote.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.gymsrote.config.login.UserDetailsImpl;
import net.gymsrote.controller.payload.request.product.CreateProductReq;
import net.gymsrote.controller.payload.request.product.CreateVariationReq;
import net.gymsrote.controller.payload.request.product.UpdateProductRequest;
import net.gymsrote.controller.payload.response.DataResponse;
import net.gymsrote.entity.user.User;
import net.gymsrote.service.ProductService;

@RestController
@RequestMapping("/api/admin/product")
public class AdminProductController {
	@Autowired
	ProductService productService;

	@GetMapping("/products")
	public ResponseEntity<?> getAllProducts(){
		return ResponseEntity.ok(productService.getAllProducts());
	}
	
	@PostMapping("/new")
	public DataResponse<?> getProductByCategory(@AuthenticationPrincipal UserDetailsImpl<User> user,
			@RequestPart("productInfo")@Valid CreateProductReq productInfo ,
			@RequestPart("avatar")@NotEmpty MultipartFile avatar ,
			@RequestPart("images")@NotEmpty List<MultipartFile> images
			) throws IOException{
		return productService.create(productInfo, avatar, images);
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<?> update(
			@PathVariable Long id,
			@RequestPart(name = "productInfo", required = false) @Valid UpdateProductRequest request,
			@RequestPart(name = "avatar", required = false) MultipartFile avatar) {
		return ResponseEntity.ok(productService.update(id, request, avatar));
	}
	
	/*@PostMapping("/variations")
	public Object addProductVariation(@AuthenticationPrincipal UserDetailsImpl<User> user,
			@RequestPart("variations")@Valid List<CreateVariationReq> variationReqs,
			@RequestPart("productId") Long proId
			) throws IOException{
		return productService.addVariation(productService.getById(proId, user), variationReqs);
		//return user.getUser().getId();
	}*/
	

}
