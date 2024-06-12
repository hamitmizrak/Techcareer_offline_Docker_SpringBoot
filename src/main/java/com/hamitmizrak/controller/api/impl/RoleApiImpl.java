package com.hamitmizrak.controller.api.impl;

import com.hamitmizrak.business.dto.RoleDto;
import com.hamitmizrak.business.services.IRoleService;
import com.hamitmizrak.controller.api.IRoleApi;
import com.hamitmizrak.data.entity.RegisterEntity;
import com.hamitmizrak.data.repository.IRegisterRepository;
import com.hamitmizrak.error.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

// LOMBOK
@RequiredArgsConstructor

// API (REST)
@RestController
@RequestMapping("/role/api/v1.0.0")
@CrossOrigin
//@CrossOrigin(origins = FrontendPortUrl.REACT_FRONTEND_PORT_URL) // http://localhost:3000
public class RoleApiImpl implements IRoleApi<RoleDto> {

    // INJECTION
    private final IRoleService iRoleService;
    private final IRegisterRepository iRegisterRepository;
    // ERROR
    private ApiResult apiResult;

    // ROLE API CREATE
    // http://localhost:4444/role/api/v1.0.0/create
    @PostMapping("/create")
    @Override
    public ResponseEntity<?> roleApiCreate(@Valid @RequestBody RoleDto roleDto) {
     RoleDto roleCreateApi= (RoleDto) iRoleService.roleServiceCreate(roleDto);
     if(roleCreateApi==null){ // Eğer Kaydederken null gelirse
         ApiResult apiResultCreate=ApiResult.builder()
                 .status(404)
                 .error("Eklenmedi")
                 .message("RoleDto Bulunamadı")
                 .path("role/api/v1.0.0/create")
                 .createdDate(new Date(System.currentTimeMillis()))
                 .build();
         return ResponseEntity.status(404).body(apiResultCreate);
     }else if(roleCreateApi.getRoleId()==0){ // Eğer Kaydederken null gelirse
            ApiResult apiResultCreate=ApiResult.builder()
                    .status(404)
                    .error("Bad Request")
                    .message("Kötü İstek ")
                    .path("role/api/v1.0.0/create")
                    .createdDate(new Date(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(400).body(apiResultCreate);
        } //end if
        return ResponseEntity.status(201).body(iRoleService.roleServiceCreate(roleDto));
    }

    // ROLE API  LIST
    // http://localhost:4444/role/api/v1.0.0/list
    @GetMapping("/list")
    @Override
    public ResponseEntity<List<RoleDto>> roleApiList() {
        return ResponseEntity.status(201).body(iRoleService.roleServiceList());
    }

    // ROLE API FIND BYID
    // http://localhost:4444/role/api/v1.0.0/find
    // http://localhost:4444/role/api/v1.0.0/find/0
    // http://localhost:4444/role/api/v1.0.0/find/1
    @GetMapping({"/find","/find/{id}"})
    @Override
    public ResponseEntity<?> roleApiFindById(@PathVariable(name = "id",required = false) Long id) {
        RoleDto roleFindApi= (RoleDto) iRoleService.roleServiceFindById(id);
        if(roleFindApi==null){ // Eğer Kaydederken null gelirse
            ApiResult apiResultList=ApiResult.builder()
                    .status(404)
                    .error("Bulumadı")
                    .message("RoleDto Bulunamadı")
                    .path("role/api/v1.0.0/create")
                    .createdDate(new Date(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(apiResultList);
        } //end if
        return ResponseEntity.ok(iRoleService.roleServiceFindById(id));
    }

    // ROLE API UPDATE
    // http://localhost:4444/role/api/v1.0.0/update
    // http://localhost:4444/role/api/v1.0.0/update/0
    // http://localhost:4444/role/api/v1.0.0/update/1
    @PutMapping({"/update","/update/{id}"})
    @Override
    public ResponseEntity<?> roleApiUpdate(@PathVariable(name = "id",required = false) Long id, @Valid @RequestBody RoleDto roleDto) {
        RoleDto roleUpdateApi= (RoleDto) iRoleService.roleServiceUpdate(id,roleDto);
        if(roleUpdateApi==null){ // Eğer Kaydederken null gelirse
            ApiResult apiResultList=ApiResult.builder()
                    .status(404)
                    .error("Güncellenmedi")
                    .message("RoleDto Bulunamadı")
                    .path("role/api/v1.0.0/create")
                    .createdDate(new Date(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(apiResultList);
        } //end if
        return ResponseEntity.ok(iRoleService.roleServiceUpdate(id,roleDto));
    }

    // ROLE API DELETE
    // http://localhost:4444/role/api/v1.0.0/delete
    // http://localhost:4444/role/api/v1.0.0/delete/0
    // http://localhost:4444/role/api/v1.0.0/delete/1
    @Override
    @DeleteMapping({"/delete", "/delete/{id}"})
    public ResponseEntity<?> roleApiDelete(@PathVariable(name = "id",required = false) Long id) {
        // System.out.println(iBlogRepository.mySpecialBlogList());

        // Role Silerken RegisterEntity veri olmaması gerekiyor
        RoleDto roleName= (RoleDto) iRoleService.roleServiceFindById(id);
        StringBuilder stringBuilder=new StringBuilder();
        List<RegisterEntity> list=  iRegisterRepository.findAllByRegisterInJoinRolesRoleName(roleName.getRoleName()); // "USER" ERole.USER.toString()
        list.forEach((temp)->{
            System.out.println(temp);
            stringBuilder.append(temp.getRegisterEmail()+" ");
        });
        if(list.size()!=0){
            apiResult = new ApiResult("localhost:4444/roles/api/v1.0.0/delete" , "Bu Rolü silemezsiniz." +"Öncelikle Kullanıcılardan, "+stringBuilder+ "Silmelisin", "Bad Request",400);
            //return ResponseEntity.badRequest().build();
            return  ResponseEntity.status(400).body(apiResult);
        }

        return  ResponseEntity.ok(iRoleService.roleServiceRoleDeleteIsNotRegister(id));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Email Adresinden Kulalnıcı rolünü bulsun
    @GetMapping("/rol/{email}")
    @Override
    public ResponseEntity<?> registerEmailFidndRole(@PathVariable(name = "email")String emailAddress) {
        return ResponseEntity.ok(iRoleService.roleServiceOnRegisterEmailAddress(emailAddress));
    }

} //end class
