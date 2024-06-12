package com.hamitmizrak.data.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

// LOMBOK
@Getter
@Setter

// Embeddable
@Embeddable
public class EmbeddableUserDetails{

    // USER DETAILS
    // Kullanıcı başlangıçta kilitli ancak mail ile aktifleştirebiliriz.
    // @Builder.Default
    @Column(name="locked")
    public Boolean isAccountNonLocked=false;

    // Eğer yaptığımız uygulamada kullanıcı 1 yıl kullanmazsa hesabı pasif olsun
    // Kullanıcı Hesap Süresi Doldu mu ?
    // @Builder.Default
    @Column(name="account_expired")
    public Boolean isAccountNonExpired=true;

    // Kullanıcı Hesap Bilgileri Süresi (Authorization)
    // @Builder.Default
    @Column(name="credentials_expired")
    public Boolean isCredentialsNonExpired=true;

    // Kullanıcı Sistemde mi ?  eğer login olmuşsa
    // @Builder.Default
    @Column(name="enabled")
    public Boolean isEnabled=true;

} //end EmbeddableUserDetails
