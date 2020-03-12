package dev.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.domains.UserExa;

@Repository
public interface UserRepository extends JpaRepository<UserExa, Long> {

	Optional<UserExa> findByUsername(String username);
}
