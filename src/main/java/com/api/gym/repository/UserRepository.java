package com.api.gym.repository;

import com.api.gym.models.Role;
import com.api.gym.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
	Optional<User> findUserByEmail(String email);
	Optional<User> findUserByProfileName(String profileName);
	Optional<List<User>> findUsersByRolesInAndIdTrainer(Set<Role> roles, Long idTrainer);

	Optional<List<User>> findUsersByRolesIn(Set<Role> roles);

	Boolean existsByEmail(String email);
	Boolean existsByConfirmationCode(String confirmationCode);
	Optional<User> findUserByConfirmationCode(String confirmationCode);

	Boolean existsByProfileName(String profileName);

	boolean deleteByProfileName(String profileName);

	Page<User> findAllByRolesIn(Set<Role> roles, Pageable pageable);
}
