package com.hamitmizrak.controller.api;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface ILoginApi<D> {

    // ROOT
    public ResponseEntity<?> root();


    // SIGN-IN
    public ResponseEntity<?> singIn(D d);

    // IS LOGIN ?
    public ResponseEntity<?> isLogin();

    // LOGOUT
    public ResponseEntity<?>logout(HttpServletRequest request, HttpServletResponse response);

    // AUTHORIZATION  (loginHandleAuthentication)
    public ResponseEntity<?> basicAuthentication (String authorization);
}
