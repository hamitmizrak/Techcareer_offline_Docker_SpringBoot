package com.hamitmizrak.controller.api.impl;

import com.hamitmizrak.business.dto.RegisterDto;
import com.hamitmizrak.business.services.IRegisterService;
import com.hamitmizrak.business.services.impl.RegisterServicesImpl;
import com.hamitmizrak.controller.api.IRegisterApi;
import com.hamitmizrak.data.entity.ForRegisterTokenEmailConfirmationEntity;
import com.hamitmizrak.error.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// LOMBOK
@RequiredArgsConstructor
@Log4j2

// API
@RestController
@RequestMapping("/register/api/v1.0.0")
@CrossOrigin
//@CrossOrigin(origins = FrontendPortUrl.REACT_FRONTEND_PORT_URL) // http://localhost:3000
public class RegisterApiImpl implements IRegisterApi<RegisterDto> {

    // INJECTION
    private final IRegisterService iRegisterService;

    // ERROR
    private ApiResult apiResult;

    // Register Services Impl
    private final RegisterServicesImpl registerServicesImpl;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // SPEED DATA
    // http://localhost:4444/register/api/v1.0.0/speed/data
    @Override
    @GetMapping("/speed/data")
    public ResponseEntity<?> registerApiSpeedData(Long key) {
        return ResponseEntity.ok(iRegisterService.registerSpeedData(key));
    }

    // ALL DELETE
    // http://localhost:4444/register/api/v1.0.0/delete/all
    @Override
    @GetMapping("/all/delete")
    public ResponseEntity<?> registerApiAllDelete() {
        return ResponseEntity.ok(iRegisterService.registerAllDelete());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // REGISTER API CREATE
    // http://localhost:4444/register/api/v1.0.0/create
    @PostMapping("/create/{roles_id}")
    @Override
    public ResponseEntity<?> registerApiCreate(@PathVariable(name = "roles_id") Long rolesId, @Valid @RequestBody RegisterDto registerDto) {
        return ResponseEntity.ok(iRegisterService.registerServiceCreate(rolesId, registerDto)) ;
    }

    // REGISTER API LIST
    // http://localhost:4444/register/api/v1.0.0/list
    @GetMapping("/list")
    @Override
    public ResponseEntity<List<RegisterDto>> registerApiList() {
        return ResponseEntity.ok(iRegisterService.registerServiceList());
    }

    // REGISTER API FIND BYID
    // http://localhost:4444/register/api/v1.0.0/find
    // http://localhost:4444/register/api/v1.0.0/find/0
    // http://localhost:4444/register/api/v1.0.0/find/1
    @GetMapping({"/find","/find/{id}"})
    @Override
    public ResponseEntity<?> registerApiFindById(@PathVariable(name = "id", required = false) Long id) {
        return ResponseEntity.ok(iRegisterService.registerServiceFindById(id));
    }

    // REGISTER API UPDATE
    // http://localhost:4444/register/api/v1.0.0/update
    // http://localhost:4444/register/api/v1.0.0/update/0
    // http://localhost:4444/register/api/v1.0.0/update/1
    @PutMapping({"/update","/update/{id}"})
    @Override
    public ResponseEntity<?> registerApiUpdate(@PathVariable(name = "id", required = false) Long id, @Valid @RequestBody RegisterDto registerDto) {
        return ResponseEntity.ok(iRegisterService.registerServiceUpdate(id,registerDto));
    }

    // REGISTER API DELETE
    // http://localhost:4444/register/api/v1.0.0/delete
    // http://localhost:4444/register/api/v1.0.0/delete/0
    // http://localhost:4444/register/api/v1.0.0/delete/1
    @Override
    @DeleteMapping({"/delete", "/delete/{id}"})
    public ResponseEntity<?> registerApiDelete(@PathVariable(name = "id", required = false) Long id) {
        return ResponseEntity.ok(iRegisterService.registerServiceDeleteById(id));
    }
   ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   // EMAIL CONFIRMATION
   // EMAIL CONFIRMATION
   //http://localhost:4444/user/api/v1/confirm?token=ca478481-5f7a-406b-aaa4-2012ebe1afb4
   @GetMapping("/confirm")
   public ResponseEntity<String> emailTokenConfirmation(@RequestParam("token") String token) {
       Optional<ForRegisterTokenEmailConfirmationEntity> tokenConfirmationEntity = registerServicesImpl.findTokenConfirmation(token);
       tokenConfirmationEntity.ifPresent(registerServicesImpl::emailTokenConfirmation);
       String html="<!doctype html>\n" +
               "<html lang=\"en\">\n" +
               "\n" +
               "<head>\n" +
               "  <title>Register</title>\n" +
               "  <meta charset=\"utf-8\">\n" +
               "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" +
               "  <style>\n" +
               "    body{\n" +
               "        background-color: black;\n" +
               "        color:white;\n" +
               "    }\n" +
               "  </style>\n" +
               "</head>\n" +
               "\n" +
               "<body>\n" +
               "\n" +
               "    <p style='padding:4rem;'>Üyeliğiniz Aktif olunmuştur.  <a href='http://localhost:3000'>Ana sayfa için tıklayınız </a></p>\n" +
               "  \n" +
               "</body>\n" +
               "</html>";
       return ResponseEntity.ok(html);
   }

} //end class
