package dev;

import java.util.Arrays;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.domains.Role;
import dev.domains.RoleUser;
import dev.domains.Userexa;
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
		Userexa admin = new Userexa();
		admin.setUsername("admin");
		admin.setPassword(passwordEncoder.encode("admin"));
		admin.setEmail("admin@exa.fr");
		admin.setRoles(Arrays.asList(new RoleUser(admin, Role.ROLE_ADMIN)));
		this.userRepository.save(admin);
	}
}
