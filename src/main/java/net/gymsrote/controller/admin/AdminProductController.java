package net.gymsrote.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.gymsrote.config.login.UserDetailsImpl;
import net.gymsrote.controller.advice.exception.InvalidInputDataException;
import net.gymsrote.controller.payload.request.PageInfoRequest;
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

	@GetMapping
	public ResponseEntity<?> getAllProducts(@RequestParam(value = "page", required=false) Integer page, @RequestBody(required=false) PageInfoRequest infoRequest){
		if(infoRequest == null) infoRequest = new PageInfoRequest();
		if(page != null) infoRequest.setCurrentPage(page);
		Pageable pageable = PageRequest.of(infoRequest.getCurrentPage(), infoRequest.getSize(), infoRequest.buildSort());
		return ResponseEntity.ok(productService.getAllProducts(pageable));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getDetails(@PathVariable("id") long id) {
		return ResponseEntity.ok(productService.getById(id, false));
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public DataResponse<?> getProductByCategory(@AuthenticationPrincipal UserDetailsImpl<User> user,
	@RequestPart("productInfo") @Valid CreateProductReq productInfo,
	@RequestPart("avatar") MultipartFile avatar ,
	@RequestPart(value = "imagesPro", required=false) List<MultipartFile> imagesPro,
	@RequestPart(value = "imagesVar", required=false) List<MultipartFile> imagesVar
			) throws IOException{
		 try{
			 if(productInfo.getVariations().size() != imagesVar.size()){
			 	throw new InvalidInputDataException("Provide image for your variation!");
			 }
			List<CreateVariationReq> var = productInfo.getVariations();
			int i=0;
			for(CreateVariationReq varReq : var) {
				varReq.setImage(imagesVar.get(i));
				i++;
			}
		 }catch(Exception e){
		 	throw new InvalidInputDataException(e.getMessage());
		 }
		return productService.create(productInfo, avatar, imagesPro);
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
