package com.api.gym.repository;

import com.api.gym.models.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long>
{
    Optional<Coupon> findById(Long id);
}
