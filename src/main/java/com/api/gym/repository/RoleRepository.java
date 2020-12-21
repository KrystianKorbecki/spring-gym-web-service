package com.api.gym.repository;


import com.api.gym.enums.ERole;
import com.api.gym.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>
{
	Optional<Role> findByName(ERole name);
//	Optional<Set<Role>> findRolesByNameIn(Set<ERole> roles);
}
