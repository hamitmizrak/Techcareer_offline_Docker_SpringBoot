package com.hamitmizrak.business.services;

import com.hamitmizrak.error.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

// D: Dto
public interface ILoginServices<D> {

    // YETKILENDIRME
    public UserDetails loadUserByUsername(String email);

    // SIGN-IN
    public Boolean singIn(D d);

    // IS LOGIN ?
    public D isLogin();

    // LOGOUT
    public D logout(HttpServletRequest request, HttpServletResponse response);

    //////////////////////////////
    // BASIC AUTHORIZATION
    public Object basicAuthentication(String authorization);

}
