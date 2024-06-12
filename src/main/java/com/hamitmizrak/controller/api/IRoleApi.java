package com.hamitmizrak.controller.api;

import org.springframework.http.ResponseEntity;
import java.util.List;

// INTERFACE (IRoleApi)
// D: Dto

public interface IRoleApi<D> {

    // ROLES CREATE
    public ResponseEntity<?> roleApiCreate(D d);

    // ROLES LIST: List List<D> yazdım
    public ResponseEntity<List<D>> roleApiList();

    // ROLES FIND
    ResponseEntity<?> roleApiFindById(Long id);

    // ROLES UPDATE
    ResponseEntity<?> roleApiUpdate(Long id, D d);

    // ROLE DELETE
    ResponseEntity<?> roleApiDelete(Long id);

    ////////////////////////////////////////////////////////////////////////
    //Email adresinden kullanı rolünü bulmak
    ResponseEntity<?>  registerEmailFidndRole(String emailAddress);

} //end interface
