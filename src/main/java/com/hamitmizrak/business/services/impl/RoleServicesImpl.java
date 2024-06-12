package com.hamitmizrak.business.services.impl;

import com.hamitmizrak.bean.ModelMapperBeanClass;
import com.hamitmizrak.business.dto.RoleDto;
import com.hamitmizrak.business.services.IRoleService;
import com.hamitmizrak.data.entity.RoleEntity;
import com.hamitmizrak.data.repository.IRoleRepository;
import com.hamitmizrak.exception.HamitMizrakException;
import com.hamitmizrak.exception.Resource404NotFoundException;
import lombok.RequiredArgsConstructor;
// import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// NOT: @Transaction Create, Delete, Update

// LOMBOK
@RequiredArgsConstructor
@Log4j2

// SERVICE
// Asıl iş Yükünü yapan yer
@Service
@Component("roleServicesImpl") //Spring'in bir parçasıdır.
public class RoleServicesImpl implements IRoleService<RoleDto, RoleEntity> {

    // INJECTION
    // 1.YOL (Field Injection)
    /*
    @Autowired
    private ModelMapperBeanClass modelMapperBeanClass;

    @Autowired
    private IRoleRepository  iRoleRepository;
    */

    // 2.YOL (Constructor Injection)
    // NOT: final yazarsam beni constructor'a zorlar
    /*
    private final ModelMapperBeanClass modelMapperBeanClass;
    private final IRoleRepository  iRoleRepository;
    @Autowired
    public RoleServicesImpl(ModelMapperBeanClass modelMapperBeanClass, IRoleRepository iRoleRepository) {
        this.modelMapperBeanClass = modelMapperBeanClass;
        this.iRoleRepository = iRoleRepository;
    }
    */

    // 3.YOL (Lombok Constructor)
    private final ModelMapperBeanClass modelMapperBeanClass;
    private final IRoleRepository iRoleRepository;

    //////////////////////////////////////////////////////////
    // MODEL MAPPER
    @Override
    public RoleDto entityToDto(RoleEntity roleEntity) {
        return modelMapperBeanClass.modelMapperMethod().map(roleEntity, RoleDto.class);
    }

    @Override
    public RoleEntity dtoToEntity(RoleDto roleDto) {
        return modelMapperBeanClass.modelMapperMethod().map(roleDto, RoleEntity.class);
    }

    //////////////////////////////////////////////////////////
    // SPEED DATA
    @Override
    public String roleSpeedData(Long data) {
        RoleEntity roleSpeedDataEntity = null; //initialize
        // RoleDto daha öncede database varsa eklemesin. NOTTTTTTTTTTTTTTTTT
        // Eğer RoleDto null değilse
        if (data != null) {
            for (int i = 1; i <= data; i++) {
                roleSpeedDataEntity.setRoleName("role_name" + UUID.randomUUID().toString()); // Büyük karakter olarak database kaydet
                RoleEntity roleSaveSecondEntity = iRoleRepository.save(roleSpeedDataEntity);
            }
        }
        return null; // Random Null ise ; null dönder.
    }

    // ALL DELETE
    @Override
    public String roleAllDelete() {
        iRoleRepository.deleteAll();
        return roleServiceList().size() + " kadar veri silindi.";
    }

    //////////////////////////////////////////////////////////
    // ROLE CRUD
    // ROLE CREATE
    // @Transaction Create, Delete, Update
    @Transactional //org.springFramework.Transaction.Optional
    @Override
    public RoleDto roleServiceCreate(RoleDto roleDto) {
        RoleEntity roleSaveFirstEntity;
        // RoleDto daha öncede database varsa eklemesin. NOTTTTTTTTTTTTTTTTT
        roleSaveFirstEntity = dtoToEntity(roleDto);
        roleSaveFirstEntity.setRoleName(roleSaveFirstEntity.getRoleName().toUpperCase()); // Büyük karakter olarak database kaydet
        RoleEntity roleSaveSecondEntity = iRoleRepository.save(roleSaveFirstEntity);
        // SET ID,  DATE
        roleDto.setRoleId(roleSaveSecondEntity.getRoleId());
        roleDto.setSystemCreatedDate(roleSaveSecondEntity.getSystemCreatedDate());
        return roleDto;
    } //end Create

    // ROLE  LIST
    @Override
    public List<RoleDto> roleServiceList() {
        // Entity List
        List<RoleEntity> roleListFirstEntity = iRoleRepository.findAll();
        // Dto List
        List<RoleDto> roleListFirstDto = new ArrayList<>();
        for (RoleEntity tempEntity : roleListFirstEntity) {
            RoleDto roleDtoFirst = entityToDto(tempEntity);
            roleListFirstDto.add(roleDtoFirst);
        } //end for
        return roleListFirstDto;
    } //end List

    // ROLE  FIND ID
    @Override
    public RoleDto roleServiceFindById(Long id) {
        //1.YOL (Optional)
        /*
         Optional<RoleEntity> roleFindByIdFirstEntity =iRoleRepository.findById(id);
        if(roleFindByIdFirstEntity.isPresent()){ // isPresent: Entity veri varsa
            return  entityToDto(roleFindByIdFirstEntity.get()); //get: veriyi getirmek
        }
         */

        // 2.YOL
        boolean resultFindData = iRoleRepository.findById(id).isPresent();

        RoleEntity roleFindByIdThirdEntity = null; // initialize:
        if (id != null) { //eğer veri varsa
            roleFindByIdThirdEntity = iRoleRepository.findById(id)
                    .orElseThrow(
                            () -> new Resource404NotFoundException(id + " nolu ID Yoktur")
                    );
        } else if (id == null) {
            throw new HamitMizrakException("Roles Dto null geldi ");
        }
        return entityToDto(roleFindByIdThirdEntity);
    } //end Find

    // ROLE  UPDATE ID, OBJECT
    // @Transaction Create, Delete, Update
    @Transactional //org.springFramework.Transaction.Optional
    @Override
    public RoleDto roleServiceUpdate(Long id, RoleDto roleDto) {
        // FIND
        RoleDto roleUpdateFindDto = roleServiceFindById(id);
        // Model Mapper
        RoleEntity roleUpdateFindEntity = dtoToEntity(roleUpdateFindDto);
        roleUpdateFindEntity.setRoleName(roleDto.getRoleName());
        iRoleRepository.save(roleUpdateFindEntity);
        // SET ID,  DATE
        roleDto.setRoleId(roleUpdateFindEntity.getRoleId());
        roleDto.setSystemCreatedDate(roleUpdateFindEntity.getSystemCreatedDate());
        return entityToDto(roleUpdateFindEntity);
    } //end update


    // Dikkat: RoleName silmek için Register veri olmaması gerekiyor.
    // @ManyToMany N - M Delete
    // Eğer RegisterDto veri varsa o kullanıcının Rolünü silemezsin.
    // DELETE
    @Transactional //org.springFramework.Transaction.Optional
    @Override
    public RoleDto roleServiceRoleDeleteIsNotRegister(Long id) {
        // FIND
        RoleDto roleDtoFind = roleServiceFindById(id);
        // Dto ==> Entity
        RoleEntity roleEntity = dtoToEntity(roleDtoFind);
        if (roleEntity != null) {
            iRoleRepository.deleteById(id);
            return roleDtoFind;
        }
        return null;
    }

    //////////////////////////////////////////////////////////
    // Email adresinden Kullanıcı Rolünü Bulmak
    // FIND
    @Override
    public RoleDto roleServiceOnRegisterEmailAddress(String emailAddress) {
        RoleEntity roleEntity = iRoleRepository.registerEmailFindRole(emailAddress);
        System.out.println(roleEntity);
        return modelMapperBeanClass.modelMapperMethod().map(roleEntity, RoleDto.class);
    }

} //end class
