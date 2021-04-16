package com.api.gym.exception;

public class CouponNotFoundException extends RuntimeException
{
    public CouponNotFoundException(Long id)
    {
        super("Error: Coupon is not found by id: " + id);
    }
}
