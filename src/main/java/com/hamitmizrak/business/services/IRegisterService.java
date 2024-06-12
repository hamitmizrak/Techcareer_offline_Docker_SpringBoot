package com.hamitmizrak.business.services;

import com.hamitmizrak.data.entity.ForRegisterTokenEmailConfirmationEntity;

import java.util.List;
import java.util.Optional;

// D: Dto
// E: Entity
public interface IRegisterService<D,E> {

    // MODEL MAPPER
    public D entityToDto(E e);
    public E dtoToEntity(D d);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // SPEED DATA
    public String registerSpeedData(Long data);

    // ALL DELETE
    public String registerAllDelete();

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // REGISTER CRUD
    // CREATE (Rolles ID)
    public D registerServiceCreate(Long rolesId, D d);

    // LIST
    public List<D> registerServiceList();

    // FIND ID
    public D registerServiceFindById(Long id);

    // UPDATE ID, OBJECT
    public D registerServiceUpdate(Long id, D d);

    // DELETE ID
    public D registerServiceDeleteById(Long id);

} //end interface
