package net.gymsrote.controller.admin;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.gymsrote.controller.payload.request.CreateCategoryRequest;
import net.gymsrote.controller.payload.request.PageInfoRequest;
import net.gymsrote.entity.EnumEntity.EProductCategoryStatus;
import net.gymsrote.service.ProductCategoryService;

@RestController
@RequestMapping("/api/admin/category")
public class AdminCategoryManage {
	
	@Autowired
	ProductCategoryService productCategoryService;
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable("id") long id,
			@RequestParam EProductCategoryStatus status){
		productCategoryService.updateProductStatus(id, status);
		return ResponseEntity.ok("");
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable("id") long id,
			@RequestBody Map<String, String> body){
		return ResponseEntity.ok(productCategoryService.update(id, body.get("name"))) ;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable("id") long id){
		return ResponseEntity.ok(productCategoryService.get(id, false)) ;
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(@RequestParam(value = "page", required=false) Integer page, @RequestBody(required=false) PageInfoRequest infoRequest){
		if(infoRequest == null) infoRequest = new PageInfoRequest();
		if(page != null) infoRequest.setCurrentPage(page);
		Pageable pageable = PageRequest.of(infoRequest.getCurrentPage(), infoRequest.getSize(), infoRequest.buildSort());
		return ResponseEntity.ok(productCategoryService.getAll(pageable)) ;
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid CreateCategoryRequest body){
		return ResponseEntity.ok(productCategoryService.create(body)) ;
	}

}
