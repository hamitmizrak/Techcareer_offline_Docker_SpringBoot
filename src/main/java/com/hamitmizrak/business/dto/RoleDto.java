package com.hamitmizrak.business.dto;

import com.hamitmizrak.annotation.AnnotationUniqueRoleName;
import com.hamitmizrak.audit.AuditingAwareBaseDto;
import com.hamitmizrak.role.ERole;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.Date;

// NOT: Validation Dto tarafından yazıyorum.

// LOMBOK
@Data
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
@Builder
// @SneakyThrows
public class RoleDto extends AuditingAwareBaseDto  implements Serializable {

    // SERILEŞTIRME
    public static final Long serialVersionUID=1L;

    // ID
    private  Long roleId;

    // DATE
    private Date systemCreatedDate;

    // Global Variable
    // @NotEmpty:  Boş değilse
    // ROLE NAME
    @NotEmpty(message = "{role.name.validation.constraints.NotNull.message}")
    @Builder.Default
    @AnnotationUniqueRoleName // Database aynı rolde bir isim varsa database ekleme
    private String roleName= ERole.USER.toString(); //default olarak USER olsun.

} //end class
