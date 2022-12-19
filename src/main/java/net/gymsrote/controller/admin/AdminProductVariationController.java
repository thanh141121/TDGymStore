package net.gymsrote.controller.admin;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import net.gymsrote.config.login.UserDetailsImpl;
import net.gymsrote.controller.payload.request.product.CreateVariationReq;
import net.gymsrote.controller.payload.request.product.UpdateProductVariationRequest;
import net.gymsrote.entity.user.User;
import net.gymsrote.service.ProductVariationService;

@RestController
@RequestMapping("/api/admin/variation/{idProduct}")
public class AdminProductVariationController {
	@Autowired
	ProductVariationService productVariationService;
	
	@PostMapping(path = "/{idVariation}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> create(
			@AuthenticationPrincipal UserDetailsImpl<User> user,
			@RequestPart("variations")@Valid CreateVariationReq variationReqs,
			@PathVariable Long idProduct
			) throws IOException{
		return ResponseEntity.ok(productVariationService.create(idProduct, variationReqs));
	}
	
	@PutMapping(path = "/{idVariation}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> update(
			@PathVariable Long idProduct,
			@PathVariable Long idVariation,
			@RequestPart @Valid UpdateProductVariationRequest body) {
		
		return ResponseEntity.ok(productVariationService.update(idVariation, idProduct, body));
	}
}
