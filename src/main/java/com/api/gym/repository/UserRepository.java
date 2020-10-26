package com.api.gym.repository;


import com.api.gym.models.Role;
import com.api.gym.models.User;
import com.api.gym.payload.response.ShowAllTrainersResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hibernate.loader.Loader.SELECT;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
	Optional<User> findByUserName(String username);
	Optional<User> findByEmail(String username);

//	@Query("select count(*) from user where create_date = :date")
//	Long findByCreateDate(Date date);

	//List<User> findAllByRoles(Set<Role> role);

	Boolean existsByUserName(String username);

	Boolean existsByEmail(String email);
	Boolean existsByCode(String code);
}
