package com.hamitmizrak.data.entity;

import com.hamitmizrak.audit.AuditingAwareBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.CreationTimestamp;
import java.io.Serializable;
import java.util.Date;

// NOT: Eğer Entity'i camelCase yazarsanız RDBMS'de underscore yazarak gösterir.

// LOMBOK
@Data
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
@Builder
// @SneakyThrows

// ENTITY
@Entity(name = "Roles") // Sql JOIN için yazdım
@Table(name = "roles")
// RegisterEntity(N) RoleEntity(M)
public class RoleEntity extends AuditingAwareBaseEntity implements Serializable {

    // SERILEŞTIRME
    public static final Long serialVersionUID = 1L;

    // ID: import jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;
    
    // Global Variable
    // ROLE NAME
    @Column(name="role_name",columnDefinition = "varchar(255) default 'USER'") //Default: USER olsun
    private String roleName;

    // Date ( otomatik tarih ekler)
    @CreationTimestamp // org.hibernate.annotation
    @Temporal(TemporalType.TIMESTAMP) // jakarta.persistence
    private Date systemCreatedDate;

} // end RoleEntity
