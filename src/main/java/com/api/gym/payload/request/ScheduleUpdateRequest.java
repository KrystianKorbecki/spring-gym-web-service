package com.api.gym.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleUpdateRequest
{
    private final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    @JsonFormat(pattern=DATE_FORMAT)
    private List<LocalDateTime> oldStartDate;

    @JsonFormat(pattern=DATE_FORMAT)
    private List<LocalDateTime> oldEndDate;

    @JsonFormat(pattern=DATE_FORMAT)
    private List<LocalDateTime> newStartDate;

    @JsonFormat(pattern=DATE_FORMAT)
    private List<LocalDateTime> newEndDate;

}
