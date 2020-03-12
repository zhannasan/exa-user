package dev;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.domains.UserExa;
import dev.repository.UserRepository;

@Component
public class StartUp {
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	/**
	 * @param userRepository
	 */
	public StartUp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@EventListener(ContextRefreshedEvent.class)
	public void onStart() {
		UserExa admin = new UserExa();
		admin.setUsername("admin");
		admin.setPassword(passwordEncoder.encode("admin"));
		this.userRepository.save(admin);
	}
}
