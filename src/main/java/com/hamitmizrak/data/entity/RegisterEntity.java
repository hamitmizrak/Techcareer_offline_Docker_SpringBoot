package com.hamitmizrak.data.entity;

import com.hamitmizrak.audit.AuditingAwareBaseEntity;
import com.hamitmizrak.data.embedded.EmbeddableUserDetails;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.CreationTimestamp;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

// LOMBOK
@Data
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
@Builder

// NOT: Eğer Entity'i camelCase yazarsanız RDBMS'de underscore yazarak gösterir.
// NOT: relation için Id ayrıca yazıyorum. BaseEntity'den otomatik almadım

// ENTITY
@Entity(name = "Registers") // Sql JOIN için yazdım
@Table(name = "registers")
// RegisterEntity(N) RoleEntity(M)
public class RegisterEntity extends AuditingAwareBaseEntity implements Serializable {

    // SERILEŞTIRME
    public static final Long serialVersionUID = 1L;

    // relation için Id ayrıca yazıyorum. BaseEntity'den otomatik almadım
    // ID: import jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "register_id")
    private Long registerId;

    // Date ( otomatik tarih ekler)
    @CreationTimestamp // org.hibernate.annotation
    @Temporal(TemporalType.TIMESTAMP) // jakarta.persistence
    private Date systemCreatedDate;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Global Variable
    // NICKNAME
    private String registerNickname;

    // NAME
    private String registerName;

    // SURNAME
    private String registerSurname;

    // IMAGE
    private String image;

    // EMAIL
    @Column(
            name = "register_email",
            nullable = false,
            updatable = false,
            insertable = true,
            length = 50,
            columnDefinition = "varchar(255) default 'my_hamitmizrak@gmail.com'")
    private String registerEmail;

    // PASSWORD
    private String registerPassword;

    // PAGE AUTHORIZATION ( O Sayfaya yekisi var mı)
    @Column(name = "page_authorization")
    private Boolean pageAuthorization = false;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // USER DETAILS (Mail Confirmation)
    // @Getter @Setter
    // @Embedded
    // @Embeddable
    // @EmbeddedId
    @Embedded
    private EmbeddableUserDetails embeddableUserDetails=new EmbeddableUserDetails();

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ROLES
    // ROLE ENTITY (RELATION)
    // NOT: @ManyToMany'de RegisterEntity RolesEntity ve 1 tane ara tablo daha oluşur adı(registers_roles)

    // 1.YOL
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name="registers_roles" ,
            joinColumns = @JoinColumn(name="register_id"), // RegisterEntity ID'si
            inverseJoinColumns = @JoinColumn(name="role_id") //RoleEntity ID'si
    )
    private Set<RoleEntity> roles=new HashSet<>();

    // 2.YOL
	/*
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name = "user_role",
	joinColumns = {
	            @JoinColumn(name = "register_id",referencedColumnName = "register_id")},
	            inverseJoinColumns = {
	                            @JoinColumn(name = "role_id",referencedColumnName = "role_id")
	                            }
	             )
	 private List<RoleEntity> roles;
	   */

} //end class entity
