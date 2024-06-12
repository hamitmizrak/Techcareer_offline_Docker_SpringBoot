package com.hamitmizrak.data.repository;

// CrudRepository<RegisterEntity,Long>
// JpaRepository<RegisterEntity,Long>
// PagingAndSortingRepository<RegisterEntity,Long>

import com.hamitmizrak.data.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity,Long> {

    // Delivered Query
    // Role name bulmak
    Optional<RoleEntity> findByRoleName(String roleName);

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    // @ManyToMany
    // RolesEntity üzerinden RegisterEntity  çağırma
    // NOT: Query: Karmaşık sorgular için kullanıyoruz.
    // NOT: registerEmailFindRole => RoleEntity üzerinden EmailAddresi Bulmak içindir.
    // NOT: EntityNameRegisters bu isim RegisterEntity Name kullandığım parametredir.
    @Query("select u from Registers t join t.roles u where t.registerEmail = :emailParam")
    RoleEntity registerEmailFindRole(@Param("emailParam") String email);

    // JPQL
    // @Query("SELECT u FROM RoleEntity u WHERE u.roleName = 1")
    // Collection<RoleEntity> kendiSorgumuYazdimJPQL();

    // Native
    // @Query( value = "SELECT * FROM RoleEntity u WHERE u.roleName = 1", nativeQuery = true)
    // Collection<RoleEntity> kendiSorgumuYazdimNative();

    // @OneToMany (Role(N)- User(M))
    //@Query("SELECT p, ci FROM UserEntity p inner join p.protectedItems pi inner join pi.contentItems ci")
    //List<Object[]> findPackages();

    //@Query(value = "SELECT * FROM e1 INNER JOIN t12 ON t12.e1_ID = e1.id WHERE t12.e2_ID = :e2ID", nativeQuery=true)
    //Collection<e1> findAllByE2ID(Long e2ID);

} //end interface
