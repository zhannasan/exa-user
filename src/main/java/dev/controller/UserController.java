package dev.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.repository.UserRepository;

@RestController
public class UserController {
	private UserRepository userRepository;

	/**
	 * @param userRepository
	 */
	public UserController(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@GetMapping("/me")
	public ResponseEntity<?> whoAmI() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return this.userRepository.findByUsername(username).map(usr -> ResponseEntity.ok(usr))
				.orElse(ResponseEntity.badRequest().build());
	}
}
