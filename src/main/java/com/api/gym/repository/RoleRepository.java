package com.api.gym.repository;

import com.api.gym.entity.Role;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>
{
    Role findByName(String role);
}
