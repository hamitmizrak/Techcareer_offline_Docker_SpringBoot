package com.hamitmizrak.business.dto;

import com.hamitmizrak.annotation.AnnotationUniqueEmailAddress;
import com.hamitmizrak.audit.AuditingAwareBaseDto;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

// LOMBOK
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Log4j2

// NOT: Validation işlemleri Dto yapalım.
// NOT: Eğer kullanıcı boşluk yazarsa trim ile almam gerekecek
// Validation Constraints Reference
// https://symfony.com/doc/current/reference/constraints.html

// REGISTER
public class RegisterDto extends AuditingAwareBaseDto implements  Serializable {

    // SERİLEŞTİRME
    public static final Long serialVersionUID = 1L;

    // ID ( Relational )
    private Long registerId;

    // DATE
    private Date systemCreatedDate;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Global Variable
    // @NotEmpty:  Boş değilse
    // NICKNAME
    @NotEmpty(message = "{register.nickname.validation.constraints.NotNull.message}")
    private String registerNickname;

    // NAME
    @NotEmpty(message = "{register.name.validation.constraints.NotNull.message}")
    private String registerName;

    // SURNAME
    @NotEmpty(message = "{register.surname.validation.constraints.NotNull.message}")
    private String registerSurname;

    // IMAGE
    private String image;

    // EMAIL
    @NotEmpty(message = "{register.email.validation.constraints.NotNull.message}")
    @Email(message = "{register.email.validation.constraints.regex.message}")
    @AnnotationUniqueEmailAddress
    private String registerEmail;

    // PASSWORD
    @NotEmpty(message = "{register.password.validation.constraints.NotNull.message}")
    @Size(min = 7, max = 15, message = "{register.password.validation.constraints.MinMax.NotNull.message}")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).*$", message = "{register.password.pattern.validation.constraints.NotNull.message}")
    // @Positive
    private String registerPassword;

    // PAGE AUTHORIZATION ( O Sayfaya yekisi var mı)
    @Builder.Default
    private Boolean pageAuthorization = false;

    // ROLLES (ENUM)
    private Collection<RoleDto> roles;

    ///////////////////////////////////////////////
    // CONSTRUCTOR (Parametresiz)
    // CONSTRUCTOR (Parametreli)
    // TOSTRING

    ///////////////////////////////////////////////
    // USER DETAILS
    // Kullanıcı register olduktan sonra Mail göndersin ve kullanıcı aktif etsin
    // Kullanıcı başlangıçta kilitli yani sisteme giremez sadece mail ile onaylanırsa aktif olur
    // @Builder.Default

    // Kullanıcı başlangıçta kilitli yani sisteme giremez sadece mail ile onaylanırsa aktif olur
    // @Builder.Default
    public Boolean isAccountNonLocked;

    // Eğer yaptığımız uygulamada kullanıcı 1 yıl kullanmazsa hesabı pasif olsun
    // Kullanıcı Hesap Süresi Doldu mu ?
    // @Builder.Default
    public Boolean isAccountNonExpired;

    // Kullanıcı Hesap Bilgileri Süresi (Authorization)
    // @Builder.Default
    public Boolean isCredentialsNonExpired;

    // Kullanıcı Sistemde mi ?  eğer login olmuşsa
    // @Builder.Default
    // Öneri SMS Api ile kullanıcıyı aktif edebilirsiniz.
    public Boolean isEnabled;

} //end class
