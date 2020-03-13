package dev.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.domains.Userexa;

@Repository
public interface UserRepository extends JpaRepository<Userexa, Long> {

	Optional<Userexa> findByUsername(String username);
}
