package com.hamitmizrak.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hamitmizrak.audit.AuditingAwareBaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;

// LOMBOK
@Getter
@Setter

// abstract CLASS
@MappedSuperclass
@JsonIgnoreProperties(value={},allowGetters = true) // json buradaki verileri takip etme
abstract public class BaseEntity extends AuditingAwareBaseEntity implements Serializable {

    // SERILEŞTIRME
    public static final Long serialVersionUID=1L;

    // ID : import jakarta.persistence.Id;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected  Long id;

    // DATE
    @Builder.Default // Lombok Default
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    protected Date systemCreatedDate=new Date(System.currentTimeMillis());

} //end class