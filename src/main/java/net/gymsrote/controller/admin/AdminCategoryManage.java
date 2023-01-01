package net.gymsrote.controller.admin;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.gymsrote.entity.EnumEntity.EProductCategoryStatus;
import net.gymsrote.service.ProductCategoryService;

@RestController
@RequestMapping("/api/admin/category")
public class AdminCategoryManage {
	
	@Autowired
	ProductCategoryService productCategoryService;
	
	@PatchMapping("/status/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable("id") long id,
			@RequestParam EProductCategoryStatus status){
		productCategoryService.updateProductStatus(id, status);
		return ResponseEntity.accepted().body(null);
	}
	
	@PatchMapping("/{id}/")
	public ResponseEntity<?> update(@PathVariable("id") long id,
			@RequestParam String name){
		return ResponseEntity.ok(productCategoryService.update(id, name)) ;
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		return ResponseEntity.ok(productCategoryService.getAll()) ;
	}

}
