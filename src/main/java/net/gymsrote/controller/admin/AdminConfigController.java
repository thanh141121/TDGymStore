package net.gymsrote.controller.admin;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.gymsrote.config.login.UserDetailsImpl;
import net.gymsrote.dto.ConfigDTO;
import net.gymsrote.entity.EnumEntity.EProductStatus;
import net.gymsrote.entity.user.User;
import net.gymsrote.service.ConfigService;

@RestController
@RequestMapping("/api/admin/config")
public class AdminConfigController {
	@Autowired
	private ConfigService service;

	@GetMapping
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(service.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@AuthenticationPrincipal UserDetailsImpl<User> user,
			@PathVariable Long id) {
		return ResponseEntity.ok(service.get(id));
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid ConfigDTO body) {
		return ResponseEntity.ok(service.create(body));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody @Valid ConfigDTO body, @PathVariable Long id) {
		return ResponseEntity.ok(service.update(id, body));
	}

	@PatchMapping(path = "/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam @NotEmpty Boolean isSelected) {
		return ResponseEntity.ok(service.updateStatus(id, isSelected));
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return ResponseEntity.ok(service.delete(id));
	}
}
