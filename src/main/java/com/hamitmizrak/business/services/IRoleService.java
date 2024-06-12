package com.hamitmizrak.business.services;

import com.hamitmizrak.business.dto.RoleDto;

import java.util.List;

// D: Dto
// E: Entity
public interface IRoleService<D,E> {

    // MODEL MAPPER
    public D entityToDto(E e);
    public E dtoToEntity(D d);

    /////////////////////////
    // SPEED DATA
    public String roleSpeedData(Long data);

    // ALL DELETE
    public String roleAllDelete();

    //////////////////////////////////////////////////////////
    // ROLE CRUD
    // ROLE CREATE
    public D roleServiceCreate(D d);

    // ROLE  LIST
    public List<D> roleServiceList();

    // ROLE  FIND ID
    public D roleServiceFindById(Long id);

    // ROLE  UPDATE ID, OBJECT
    public D roleServiceUpdate(Long id, D d);

    // ROLE  DELETE ID
    // @ManyToMany N - M Delete
    // Eğer RegisterDto veri varsa o kullanıcının Rolünü silemezsin.
    RoleDto roleServiceRoleDeleteIsNotRegister(Long id);

    //////////////////////////////////////////////////////////
    // Register Email adresinden Kullanıcı Rolünü Bulmak
    public D roleServiceOnRegisterEmailAddress(String emailAddress);

} //end class
