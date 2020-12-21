package com.api.gym.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Convert
public class DateConverter implements AttributeConverter<LocalDateTime, Timestamp>
{

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime attribute)
    {
        if(attribute == null)
        {
            return null;
        }
        else
        {
            return Timestamp.valueOf(attribute);
        }
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp dbData)
    {
        if(dbData == null)
        {
            return null;
        }
        else
        {
            return dbData.toLocalDateTime();
        }
    }
}
