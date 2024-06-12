package com.hamitmizrak.business.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

// LOMBOK
@Log4j2
@Service
public class LoginInformation {

    public Optional<String>  isLogin(){
        // database ile login girişi yapmış kullanıcı bilgilerini almak
        // org.springframework.security.core.Authentication;
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        // Eğer kullanıcı giriş yapmışsa session bilgilerini almak
        if(authentication!=null && authentication.isAuthenticated()){
            log.warn("Sistemde Kullanı vardır.");
            System.out.println("Name: "+authentication.getName());
            System.out.println("Principal: "+authentication.getPrincipal());
            return Optional.of(authentication.getName());
        }
       /* else if(authentication==null && !authentication.isAuthenticated()){
            log.error("Sistemde Kullanı yoktur");
            return Optional.empty();
        }*/
        return null;
    } // isLogin
} //end LoginInformation
