package com.api.gym.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class MainAdminData extends MainSiteData
{
    private Integer soldTicket;
    private Integer numberOfTrainers;
    private Integer numberOfUsers;
    private Integer numberOfModerators;
    private Double moneyForSoldTickets;
}
