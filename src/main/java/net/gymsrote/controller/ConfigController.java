package net.gymsrote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.gymsrote.config.login.UserDetailsImpl;
import net.gymsrote.entity.user.User;
import net.gymsrote.service.ConfigService;

@RestController
@RequestMapping("/api/config")
public class ConfigController {
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
}
