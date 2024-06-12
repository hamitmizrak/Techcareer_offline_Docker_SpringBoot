package com.hamitmizrak.controller.api;

import org.springframework.http.ResponseEntity;

import java.util.List;

// INTERFACE (IRegisterApi)
// D: Dto

public interface IRegisterApi<D> {

    // SPEED DATA
    public ResponseEntity<?> registerApiSpeedData(Long key);

    // ALL DELETE
    public ResponseEntity<?> registerApiAllDelete();

    ////////////////////////////////////////////////////////////////////////
    // REGISTER CREATE
    public ResponseEntity<?> registerApiCreate(Long rolesId, D d);

    // REGISTER LIST
    public ResponseEntity<List<D>> registerApiList();

    // REGISTER FIND
    ResponseEntity<?> registerApiFindById(Long id);

    // REGISTER UPDATE
    ResponseEntity<?> registerApiUpdate(Long id, D d);

    // REGISTER DELETE
    ResponseEntity<?> registerApiDelete(Long id);

    ////////////////////////////////////////////////////////////////////////
    //Email adresinde kullanı rolünü bulmak

} //end interface
