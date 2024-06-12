package com.hamitmizrak.business.services.impl;

import com.hamitmizrak.bean.ModelMapperBeanClass;
import com.hamitmizrak.bean.PasswordEncoderBeanClass;
import com.hamitmizrak.business.dto.RegisterDto;
import com.hamitmizrak.business.services.ILoginServices;
import com.hamitmizrak.data.entity.RegisterEntity;
import com.hamitmizrak.data.repository.IRegisterRepository;
import com.hamitmizrak.error.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// LOMBOK
@Log4j2

/*
UserDetailsService: Sisteme Login Girmiş kullanıcı bilgilerini almak
 */

// SERVICE
// Asıl iş Yükünü yapan yer
@Service
@Component("loginServicesImpl") //Spring'in bir parçasıdır.
public class LoginServicesImpl implements UserDetailsService, ILoginServices<RegisterDto> {

    // INJECTION

    private  IRegisterRepository iRegisterRepository;

    private  PasswordEncoderBeanClass passwordEncoderBeanClass; // for Basic Authentication

    private  ModelMapperBeanClass modelMapperBeanClass;

    @Autowired
    public LoginServicesImpl(IRegisterRepository iRegisterRepository, PasswordEncoderBeanClass passwordEncoderBeanClass, ModelMapperBeanClass modelMapperBeanClass) {
        this.iRegisterRepository = iRegisterRepository;
        this.passwordEncoderBeanClass = passwordEncoderBeanClass;
        this.modelMapperBeanClass = modelMapperBeanClass;
    }

    public LoginServicesImpl() {
    }

    //////////////////////////////////////////////////////////////////////

    // Load User By Username
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        RegisterEntity registerEntity = iRegisterRepository.findByRegisterEmail(email).orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı"));
        return new CustomiseUserDetailsImpl(registerEntity);
    }

    //////////////////////////////////////////////////////////////////////
    // SIGN-IN
    @Override
    public Boolean singIn(RegisterDto registerDto) {
        return Boolean.FALSE;
    }

    // IS LOGIN
    @Override
    public RegisterDto isLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println(authentication.getName());
            System.out.println(authentication.getPrincipal());
            RegisterDto registerDto = RegisterDto.builder()
                    .registerName(authentication.getName())
                    .build();
            return registerDto;
        }
        return null;
    }

    // LOGOUT
    @Override
    public RegisterDto logout(HttpServletRequest request, HttpServletResponse response) {

        // Login Olmuş Birisi mi var mı ?
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            RegisterDto registerDto = RegisterDto.builder().registerName("logout name").build();
            return registerDto;
        }
        return null;
    }

    // BASIC AUTHENTICATION
    // Dikkat: Basic Authenticationda mutlaka username yazısı olarak gelmedir. email yerine biz username kullanıyoruz
    @Override
    public Object basicAuthentication(String authorization) {
        // authorization Null İse
        if (authorization == null) {
            return null;
        } else if (authorization != null) { // authorization Null değilse
            // authorization Null değilse
            log.info(authorization);
            // Basic OxRMWw==
            // OxRMWw==Encoder-Decoder demektir.

            // Not: 0=Basic   1=OxRMWw==
            String base64EncoderSplitParse = authorization.split("Basic")[1];  //1=OxRMWw==

            // Şifreyi çözmek
            // OxRMWw==  şifre çözülmüş hali  username:password
            String base64Decoder = new String(Base64.getDecoder().decode(base64EncoderSplitParse));  //java.util.Base64

            //  username:password bunları parçalamak
            String[] usernamePasswordParse = base64Decoder.split(":");

            // username (email)
            String username = usernamePasswordParse[0];
            System.out.println("Username: " + username);
            log.info("Username: " + username);

            // password
            String password = usernamePasswordParse[1];
            System.out.println("password: " + password);
            log.info("password: " + password);

            ////////////////////////////////////////////
            //Not: username aslında email bunu unutma
            Optional<RegisterEntity> registerEntityOptional =  iRegisterRepository.findByRegisterEmail(username);

            // Eğer Sistemde Kullanıcı Yoksa
            if(registerEntityOptional.isPresent()==false){
                ApiResult result=new ApiResult("/login/api/v1.0.0/authentication","Kullanıcı Bulunamadı",404);
            }// Kullanıcı bilgileri doğru ancak kilitliyse
            else if(registerEntityOptional.isPresent()==true&&registerEntityOptional.get().getEmbeddableUserDetails().getIsAccountNonLocked()==false){
                ApiResult result=new ApiResult("/login/api/v1.0.0/authentication","Kullanıcı Kilitli",401);
            }// Kullanıcı bilgileri doğru ancak süresi dolmuşsa
            else if(registerEntityOptional.isPresent()==true&&registerEntityOptional.get().getEmbeddableUserDetails().getIsAccountNonExpired()==false){
                ApiResult result=new ApiResult("/login/api/v1.0.0/authentication","Kullanıcı Süresi Bitmiş",401);
            }else{
                String hashingPassword=registerEntityOptional.get().getRegisterPassword();
                // Hashing olmuş şifreyi kullanıcının verdiği şifre ile karşılatırmak
                if(passwordEncoderBeanClass.passwordEncoderMethod().matches(password,hashingPassword)){
                    Map<String,Object> mapRegisterEntity=new HashMap<>();
                    mapRegisterEntity.put("nickname",registerEntityOptional.get().getRegisterNickname());
                    mapRegisterEntity.put("name",registerEntityOptional.get().getRegisterName());
                    mapRegisterEntity.put("surname",registerEntityOptional.get().getRegisterSurname());
                    mapRegisterEntity.put("image",registerEntityOptional.get().getImage());
                    mapRegisterEntity.put("email",registerEntityOptional.get().getRegisterEmail());
                    mapRegisterEntity.put("password",registerEntityOptional.get().getRegisterPassword());
                    mapRegisterEntity.put("system_created_date",registerEntityOptional.get().getSystemCreatedDate());
                    mapRegisterEntity.put("created_user",registerEntityOptional.get().getCreatedUser());
                    mapRegisterEntity.put("created_date",registerEntityOptional.get().getCreatedDate());
                    mapRegisterEntity.put("last_user",registerEntityOptional.get().getLastUser());
                    mapRegisterEntity.put("last_date",registerEntityOptional.get().getLastDate());
                    return modelMapperBeanClass.modelMapperMethod().map(mapRegisterEntity, RegisterDto.class);
                }else{
                    ApiResult result=new ApiResult("/login/api/v1.0.0/authentication","kullanıcı adı veya şifre yanlış",401);
                } //end else matches
            } //end else
        } //end else-if
        return null;
    }

} //end services
