package net.gymsrote.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
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
import net.gymsrote.entity.EnumEntity.EOrderStatus;
import net.gymsrote.entity.product.Product;
import net.gymsrote.entity.product.Product_;
import net.gymsrote.entity.user.User;
import net.gymsrote.repository.search.CustomProductRepository;
import net.gymsrote.repository.search.Filter;
import net.gymsrote.repository.search.QueryOperator;
import net.gymsrote.service.OrderService;
import net.gymsrote.service.ProductService;
import net.gymsrote.service.Recommendation.Recommender;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	@Autowired
	ProductService productService;
	
	@Autowired
	CustomProductRepository customProductRepository;

	@Autowired
	Recommender recommender;
	@Autowired
	OrderService orderService;
	
	@GetMapping("/search/filter")
	public ResponseEntity<?> searchWithFilterProducts(

			@RequestParam EOrderStatus status,
			@AuthenticationPrincipal UserDetailsImpl<User> user,
			@RequestParam(required = false) Integer page,
			@RequestBody(required=false) PageInfoRequest infoRequest
			){
		Filter lowRange = Filter.builder()
			    .field(Product_.MIN_PRICE)
			    .operator(QueryOperator.LESS_THAN)
			    .value("5000000")
			    .build();
		
		Filter namelike = Filter.builder()
			    .field(Product_.NAME)
			    .operator(QueryOperator.LIKE)
			    .value("whey")
			    .build();
		List<Filter> filters = new ArrayList<>();
		filters.add(lowRange);
		filters.add(namelike);
//		return ResponseEntity.ok( customProductRepository.getQueryResult(filters));
			
			if(infoRequest == null) infoRequest = new PageInfoRequest();
			if(page != null) infoRequest.setCurrentPage(page);
			Pageable pageable = PageRequest.of(infoRequest.getCurrentPage(), infoRequest.getSize(), Sort.by(Sort.Direction.DESC, "id"));

			return ResponseEntity.ok(
				orderService.getByStatus(
					user.getUser(),
					status,
					pageable
				));
	}
	
	@GetMapping
	public ResponseEntity<?> getAllProducts(@RequestParam(value = "page", required=false) Integer page, 
			@RequestParam(value = "category", required=false) Long category, 
			@RequestBody(required=false) PageInfoRequest infoRequest){
		if(infoRequest == null) infoRequest = new PageInfoRequest();
		if(page != null) infoRequest.setCurrentPage(page);
		Pageable pageable = PageRequest.of(infoRequest.getCurrentPage(), infoRequest.getSize(), infoRequest.buildSort());
		return ResponseEntity.ok(productService.getAllProducts(pageable, true, category));
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> searchProducts(@RequestParam String key){
		PageInfoRequest request = new PageInfoRequest();
		Pageable pageable = PageRequest.of(request.getCurrentPage(), request.getSize());
		return ResponseEntity.ok(productService.search(key,pageable));
	}
	
	//Item-Based 
	@GetMapping("/recommend")
	public ResponseEntity<?> recommendforUser(@AuthenticationPrincipal UserDetailsImpl<User> user) throws IOException, TasteException{

		return ResponseEntity.ok(recommender.recommendItems(user.getUser().getId(), 10));
	}
	
	//Content-based
	@GetMapping("/recommend/{productId}")
    public ResponseEntity<?>  recommendProducts(@PathVariable int productId) throws InterruptedException {
		return  ResponseEntity.ok(productService.recommendContentBased(productId));
    }
	
	// @GetMapping
	// public ResponseEntity<?> searchProducts(@RequestParam String key){
	// 	PageInfoRequest request = new PageInfoRequest();
	// 	Pageable pageable = PageRequest.of(request.getCurrentPage(), request.getSize());
		
	// 	Filter lowRange = Filter.builder()
	// 		    .field(Product_.MIN_PRICE)
	// 		    .operator(QueryOperator.LESS_THAN)
	// 		    .value("10000000")
	// 		    .build();
	// 	List<Filter> filters = new ArrayList<>();
	// 	filters.add(lowRange);
	// 	List<Product> list1 =  customProductRepository.getQueryResult(filters);
	// 	List<Product> list2 = productService.search(key);
	// 	List<Product> list = new ArrayList<>();
	// 	for (Product t : list1) {
    //         if(list2.contains(t)) {
    //             list.add(t);
    //         }
    //     }
	// 	return ResponseEntity.ok(serviceUtils.convertToListResponse(list, Product.class));
	// 	return ResponseEntity.ok(productService.getAllProducts());
	// }
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getDetails(@PathVariable("id") long id) {
		return ResponseEntity.ok(productService.getById(id, true));
	}

	@GetMapping("/sale")
	public ResponseEntity<?> getTopSale() {
		return ResponseEntity.ok(productService.getTopSaleProduct());
	}
	
	@GetMapping("/lasted")
	public ResponseEntity<?> getTopLasted() {
		return ResponseEntity.ok(productService.getTopLastedProduct());
	}
	
	@GetMapping("/most-viewed")
	public ResponseEntity<?> getTopViewed() {
		return ResponseEntity.ok(productService.getTopVisitProduct());
	}
	
	@GetMapping("/most-sold")
	public ResponseEntity<?> getTopSold() {
		return ResponseEntity.ok(productService.getTopSoldProduct());
	}
}