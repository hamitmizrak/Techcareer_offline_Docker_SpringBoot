package com.hamitmizrak.business.dto;

import com.hamitmizrak.audit.AuditingAwareBaseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

//LOMBOK
@Getter
@Setter

// abstract CLASS
abstract public class BaseDto extends AuditingAwareBaseDto implements Serializable {

    // Serileştirme
    public static final Long serialVersionUID=1L;

    // ID
    protected  Long id;

    // DATE
    @Builder.Default // Lombok Default
    protected Date systemCreatedDate=new Date(System.currentTimeMillis());
} //end class
