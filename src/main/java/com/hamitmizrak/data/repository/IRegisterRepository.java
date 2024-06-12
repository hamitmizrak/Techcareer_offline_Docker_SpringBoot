package com.hamitmizrak.data.repository;

import com.hamitmizrak.data.entity.RegisterEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// CrudRepository<RegisterEntity,Long>
// JpaRepository<RegisterEntity,Long>
// PagingAndSortingRepository<RegisterEntity,Long>

@Repository
public interface IRegisterRepository extends CrudRepository<RegisterEntity, Long> {

    // Delivered Query
    // Emailden kullanıcı objesini bulmak
    // findByRegisterEmail: buradaki R register'ının kısaltması için kullandım.
    Optional<RegisterEntity> findByRegisterEmail(String email);

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    // RegisterEntity üzerinde RoleEntity'e bağlama (Join)
    // RegisterEntity üzerinden RolesEntity çağırma
    // NOT: Query: Karmaşık sorgular için kullanıyoruz.
    // NOT: findAllByRegisterInJoinRolesRoleName => RegisterEntity üzerinden RolesName Bulmak içindir.
    // NOT: EntityNameRegisters bu isim RegisterEntity Name kullandığım parametredir.
    @Query("select reg from Registers reg join reg.roles rol where rol.roleName= :roleNameParam")
    List<RegisterEntity> findAllByRegisterInJoinRolesRoleName(@Param("roleNameParam") String roleName);

} //end interface
