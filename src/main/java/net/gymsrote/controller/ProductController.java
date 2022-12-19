package net.gymsrote.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import net.gymsrote.controller.payload.response.DataResponse;
import net.gymsrote.entity.product.Product_;
import net.gymsrote.entity.user.User;
import net.gymsrote.repository.search.CustomProductRepository;
import net.gymsrote.repository.search.Filter;
import net.gymsrote.repository.search.QueryOperator;
import net.gymsrote.service.ProductService;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
	@Autowired
	ProductService productService;
	
	@Autowired
	CustomProductRepository customProductRepository;
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public DataResponse<?> getProductByCategory(@AuthenticationPrincipal UserDetailsImpl<User> user,
			@RequestPart("productInfo")@Valid CreateProductReq productInfo ,
			@RequestPart("avatar")@NotEmpty MultipartFile avatar ,
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
	
	@GetMapping("/search/filter")
	public ResponseEntity<?> searchWithFilterProducts(){
		Filter lowRange = Filter.builder()
			    .field(Product_.MIN_PRICE)
			    .operator(QueryOperator.LESS_THAN)
			    .value("10000000")
			    .build();
		List<Filter> filters = new ArrayList<>();
		filters.add(lowRange);
		return ResponseEntity.ok( customProductRepository.getQueryResult(filters));
	}
	
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
	
	/*@GetMapping("/search")
	public ResponseEntity<?> searchProducts(@RequestParam String key){
		PageInfoRequest request = new PageInfoRequest();
		Pageable pageable = PageRequest.of(request.getCurrentPage(), request.getSize());
		
		Filter lowRange = Filter.builder()
			    .field(Product_.MIN_PRICE)
			    .operator(QueryOperator.LESS_THAN)
			    .value("10000000")
			    .build();
		List<Filter> filters = new ArrayList<>();
		filters.add(lowRange);
		List<Product> list1 =  customProductRepository.getQueryResult(filters);
		List<Product> list2 = productService.search(key);
		List<Product> list = new ArrayList<>();
		for (Product t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }
		return ResponseEntity.ok(serviceUtils.convertToListResponse(list, Product.class));
//		return ResponseEntity.ok(productService.getAllProducts());
	}*/
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getDetails(@PathVariable("id") long id, @AuthenticationPrincipal UserDetailsImpl<User> user) {
		return ResponseEntity.ok(productService.getById(id, user));
	}
}