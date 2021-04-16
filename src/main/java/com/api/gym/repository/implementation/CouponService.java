package com.api.gym.repository.implementation;

import com.api.gym.exception.CouponNotFoundException;
import com.api.gym.models.Coupon;
import com.api.gym.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class CouponService
{
    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository)
    {
        this.couponRepository = couponRepository;
    }

    public Coupon findById(Long id)
    {
        return couponRepository.findById(id).orElseThrow(() -> new CouponNotFoundException(id));
    }
}
