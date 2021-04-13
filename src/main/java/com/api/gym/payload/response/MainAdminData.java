package com.api.gym.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainAdminData
{
    private Integer newUsers;
    private Integer soldTicket;
    private Integer numberOfTrainingSession;
    private Integer numberOfTrainers;
    private Integer numberOfUsers;
    private Integer numberOfModerators;
    private Double moneyForSoldTickets;
}
