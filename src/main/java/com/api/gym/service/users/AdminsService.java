package com.api.gym.service.users;

import com.api.gym.converters.Converter;
import com.api.gym.enums.ERole;
import com.api.gym.models.Coupon;
import com.api.gym.models.Ticket;
import com.api.gym.models.UserTicket;
import com.api.gym.payload.response.MainAdminData;
import com.api.gym.repository.implementation.*;
import com.sun.xml.bind.v2.TODO;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AdminsService
{
    private final UserService userService;
    private final Converter converter;
    private final CompleteTrainingPlanService completeTrainingPlanService;
    private final UserTicketService userTicketService;
    private final TicketService ticketService;
    private final CouponService couponService;

    AdminsService(UserService userService, Converter converter, CompleteTrainingPlanService completeTrainingPlanService, UserTicketService userTicketService, TicketService ticketService, CouponService couponService)
    {
        this.userService = userService;
        this.converter = converter;
        this.completeTrainingPlanService = completeTrainingPlanService;
        this.userTicketService = userTicketService;
        this.ticketService = ticketService;
        this.couponService = couponService;
    }

    /**
     * This method is created for users with ADMIN_ROLE, shows main data about sold ticket, new users,earned money etc.
     * @return {@link MainAdminData} with number of users,moderators, trainers, new users, number of training session, sold ticket and earned money
     */
    public List<MainAdminData> primaryDataForAdmin()
    {
        Timestamp todayStart = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        Timestamp todayEnd = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        Double earnedMoney = 0.0;

        for(UserTicket userTicket : userTicketService.findAllByPurchaseDateBetween(todayStart, todayEnd))
        {
            Coupon coupon = couponService.findById(userTicket.getIdCoupon());
            Ticket ticket = ticketService.findById(userTicket.getIdTicket());
            if(coupon.getDiscount() != 0.0)
            {
                earnedMoney += ticket.getPrice() - coupon.getDiscount();
            }
            else if(coupon.getDiscountPercent() != 0.0)
            {
                earnedMoney += ticket.getPrice() * coupon.getDiscountPercent() / 100;
            }
            else
            {
                earnedMoney += ticket.getPrice();
            }

        }

        MainAdminData mainAdminDataToday = MainAdminData.builder()
                .numberOfUsers(userService.countUsersByRolesIn(converter.convertERoleToRolesSet(ERole.ROLE_USER)))
                .numberOfModerators(userService.countUsersByRolesIn(converter.convertERoleToRolesSet(ERole.ROLE_MODERATOR)))
                .numberOfTrainers(userService.countUsersByRolesIn(converter.convertERoleToRolesSet(ERole.ROLE_TRAINER)))
                .newUsers(userService.countUsersByRolesIn(converter.convertERoleToRolesSet(ERole.ROLE_USER)))
                .numberOfTrainingSession(completeTrainingPlanService.countAllByEndDateBetween(todayStart, todayEnd))
                .soldTicket(userTicketService.countAllByPurchaseDateBetween(todayStart, todayEnd))
                .moneyForSoldTickets(earnedMoney)
                .build();
        //TODO Add a data for admin with this week, month and year
        return List.of(mainAdminDataToday);

    }


}
