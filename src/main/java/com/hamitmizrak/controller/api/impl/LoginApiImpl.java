package com.hamitmizrak.controller.api.impl;

import com.hamitmizrak.business.dto.RegisterDto;
import com.hamitmizrak.business.services.ILoginServices;
import com.hamitmizrak.business.services.impl.CustomiseUserDetailsImpl;
import com.hamitmizrak.controller.api.ILoginApi;
import com.hamitmizrak.error.ApiResult;
import com.hamitmizrak.utils.FrontendPortUrl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

// 3 9 4 7
// LOMBOK
@RequiredArgsConstructor

// REST
@RestController
@CrossOrigin(origins = FrontendPortUrl.REACT_FRONTEND_PORT_URL)
@RequestMapping("/login/api/v1.0.0")
public class LoginApiImpl implements UserDetailsService, ILoginApi<RegisterDto> {

    // INJECTION
    private final ILoginServices iLoginServices;
    private final CustomiseUserDetailsImpl customiseUserDetails; //UserDetails

    ////////////////////////////////////////////////////////////////////////////////////////
    // http://localhost:4444/login/api/v1.0.0/root
    @Override
    @GetMapping("/root")
    public ResponseEntity<?> root() {
        return ResponseEntity.status(200).body("index");
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // Load User By Username
    // http://localhost:4444/login/api/v1.0.0/user/details/username
    @Override
    @GetMapping("/user/details/username")
    public  UserDetails loadUserByUsername(String username) {
        return iLoginServices.loadUserByUsername(username);
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // SIGN-IN
    // http://localhost:4444/login/api/v1.0.0/signin
    @Override
    @PostMapping("/signin")
    public ResponseEntity<?> singIn(@Valid @RequestBody RegisterDto registerDto) {
      Boolean result=  iLoginServices.singIn(registerDto);
      if(result){
          return ResponseEntity.ok(registerDto);
      }
        return ResponseEntity.ok(ApiResult.builder().status(400).message("Giriş Bilgileriniz Yanlıştır").path("/signin").build());
    }

    // IS LOGIN ?
    // http://localhost:4444/login/api/v1.0.0/islogin
    @Override
    @PostMapping("/islogin")
    public ResponseEntity<?>  isLogin() {
        if( iLoginServices.isLogin()!=null){
            return ResponseEntity.ok(iLoginServices.isLogin());
        }
        return ResponseEntity.ok(ApiResult.builder().status(400).message("Giriş yapılmamıştır").path("/is-login").build());
    }

    // LOGOUT
    // http://localhost:4444/login/api/v1.0.0/logout
    @GetMapping("/logout")
    @Override
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        // Login Olmuş Birisi mi var mı ?
        if(iLoginServices.logout(request,response)!=null){
            return ResponseEntity.ok(ApiResult.builder().status(200).message("Güvenli Çıkış Yapıldı").path("/logout").build());
        }
        // return new HamitMizrakException("Güvenli Çıkış Yapılmadı !!!");
        return ResponseEntity.ok(ApiResult.builder().status(400).message("Güvenli Çıkış Yapılmadı !!!").path("/logout").build());
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // BASIC AUTHENTICATION (loginHandleAuthentication)
    // http://localhost:4444/login/api/v1.0.0/authentication
    @Override
    @PostMapping("/authentication")
    public ResponseEntity<?> basicAuthentication (@RequestHeader(name="Authorization", required = false)  String authorization) {
        return ResponseEntity.ok(iLoginServices.basicAuthentication(authorization));
    }

} // end class
