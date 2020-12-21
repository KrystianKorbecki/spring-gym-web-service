package com.api.gym.models;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.array.TimestampArrayType;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@TypeDefs({
    @TypeDef(
        name = "string-array",
        typeClass = StringArrayType.class
    ),
    @TypeDef(
        name = "int-array",
        typeClass = TimestampArrayType.class
    )

})
@MappedSuperclass
public class BaseEntity
{
 
    @Id
    private Long id;
 
    @Version
    private Integer version;
 
}