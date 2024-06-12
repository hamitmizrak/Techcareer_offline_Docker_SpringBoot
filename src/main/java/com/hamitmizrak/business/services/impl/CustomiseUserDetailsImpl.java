package com.hamitmizrak.business.services.impl;

import com.hamitmizrak.data.entity.RegisterEntity;
import com.hamitmizrak.data.entity.RoleEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

// UserDetails kendimize göre (customise) yapan Class
// NOT: UserDetails interface eklendi

// LOMBOK
@Log4j2

// SERVICES
@Service
public class CustomiseUserDetailsImpl implements UserDetails  {

    // INJECTION
    private  RegisterEntity registerEntity;

    public CustomiseUserDetailsImpl() {
        registerEntity=new RegisterEntity();
    }

    public CustomiseUserDetailsImpl(RegisterEntity registerEntity) {
        this.registerEntity = registerEntity;
    }

    // Yetkilendirme
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // ROLE
        Set<RoleEntity> roleEntitySet=registerEntity.getRoles();

        // YETKI
        List<SimpleGrantedAuthority> simpleGrantedAuthorities= new ArrayList<>();

        // YETKILERI VER
        for(RoleEntity roleEntity: roleEntitySet){
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(roleEntity.getRoleName()));
        }
        return simpleGrantedAuthorities;
    }

    // Password
    @Override
    public String getPassword() {
        return registerEntity.getRegisterPassword();
    }

    // Email (Username)
    @Override
    public String getUsername() {
        return registerEntity.getRegisterEmail();
    }

    // Kullanıcı Süresi
    @Override
    public boolean isAccountNonExpired() {
        return registerEntity.getEmbeddableUserDetails().isAccountNonExpired;
    }

    // Kullanıcı Kiliti
    @Override
    public boolean isAccountNonLocked() {
        return registerEntity.getEmbeddableUserDetails().isAccountNonLocked;
    }

    // Kimlik bilgilerini Kontrol Etmek
    @Override
    public boolean isCredentialsNonExpired() {
        return registerEntity.getEmbeddableUserDetails().isCredentialsNonExpired;
    }

    // Kullanıcı Aktif mi
    @Override
    public boolean isEnabled() {
        return registerEntity.getEmbeddableUserDetails().isEnabled;
    }

} //end class
