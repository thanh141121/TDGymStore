package net.gymsrote.controller.admin;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.gymsrote.service.UserService;
import net.gymsrote.utility.PagingInfo;

@RestController
@RequestMapping("/api/admin/user-manage")
public class AdminUserManageController {
	@Autowired
	UserService userService;

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getById(id));
	}

	@GetMapping
	public ResponseEntity<?> getAlls(
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size,
			@RequestParam(required = false) @Min(1) @Max(3) Integer sortBy,
			@RequestParam(required = false) Boolean sortDescending) {

		return ResponseEntity.ok(
				userService.getAll(
						new PagingInfo(page, size, sortBy, sortDescending)));
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable("id") Long id,
			@RequestParam Boolean isEnabled){
		userService.updateStatus(id, isEnabled);
		return ResponseEntity.ok("");
	}
}
