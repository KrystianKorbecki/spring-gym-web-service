package com.api.gym.repository;


import com.api.gym.models.Role;
import com.api.gym.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
	Optional<User> findByUserName(String username);
	Optional<User> findAllByEmail(String username);
	User findUserByEmail(String email);
//	@Query("select count(*) from user where create_date = :date")
//	Long findByCreateDate(Date date);

	List<User> findAllByRolesIn(Set<Role> roles);

	Boolean existsByUserName(String username);

//	@Modifying
//	@Query("")
//	Boolean updateUserRoleByEmail(String email);

	Boolean existsByEmail(String email);
	Boolean existsByCode(String code);
}
