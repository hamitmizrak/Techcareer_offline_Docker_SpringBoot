package com.hamitmizrak.runner;

import com.hamitmizrak.bean.ModelMapperBeanClass;
import com.hamitmizrak.business.dto.RegisterDto;
import com.hamitmizrak.business.services.IRegisterService;
import com.hamitmizrak.data.entity.RegisterEntity;
import com.hamitmizrak.data.entity.RoleEntity;
import com.hamitmizrak.data.repository.IRoleRepository;
import com.hamitmizrak.role.ERole;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


// LOMBOK
@RequiredArgsConstructor // Injection

// Command Line Runner
@Log4j2
@Component
// NOT: implement CommandLineRunner
public class BlogCommandLineRunner_1_Register_Email implements CommandLineRunner {

    // INJECTION
    private final IRoleRepository iRoleRepository;
    private final IRegisterService iRegisterServices;
    private final ModelMapperBeanClass modelMapperBeanClass;

    ///////////////////////////////////////////////////

    // Global Variable
    /*
    Application.properties
    WebSecurity icin kullanacagım
    spring.security.superadmin.name=super
    spring.security.superadmin.password=root
    spring.security.superadmin.roles=SUPERADMIN
    */

    // @Value => org.springframework.beans.factory.annotation.Value;
    @Value("${spring.command.line.rules.superadmin.nickname}")
    private String applicationSuperAdminNickname;

    @Value("${spring.command.line.rules.superadmin.name}")
    private String applicationSuperAdminName;

    @Value("${spring.command.line.rules.superadmin.surname}")
    private String applicationSuperAdminSurname;

    @Value("${spring.command.line.rules.superadmin.email}")
    private String applicationSuperAdminEmail;

    @Value("${spring.command.line.rules.superadmin.password}")
    private String applicationSuperAdminPassword;

    @Value("${spring.command.line.rules.superadmin.roles}")
    private String applicationSuperAdminRules;

    ///////////////////////////////////////////////////
    // SUPER ADMIN (1)
    private void superAdmin() {
        synchronized (this) {
            // Multithreading Senkron
            /*
            Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
            }
            });
            */

            // SUPER ADMIN ROLES
            Long superRoleId = iRoleRepository.save(RoleEntity.builder().roleId(1L).roleName(applicationSuperAdminRules).build()).getRoleId();

            // SUPER ADMIN INFORMATION
            RegisterEntity registerEntity=new RegisterEntity();
            registerEntity.setRegisterNickname(applicationSuperAdminNickname);
            registerEntity.setRegisterName(applicationSuperAdminName);
            registerEntity.setRegisterSurname(applicationSuperAdminSurname);
            registerEntity.setRegisterEmail(applicationSuperAdminEmail);
            registerEntity.setRegisterPassword(applicationSuperAdminPassword);

            // USERDETAILS
            registerEntity.getEmbeddableUserDetails().setIsAccountNonLocked(true);
            registerEntity.getEmbeddableUserDetails().setIsCredentialsNonExpired(true);
            registerEntity.getEmbeddableUserDetails().setIsAccountNonExpired(true);
            registerEntity.getEmbeddableUserDetails().setIsEnabled(true);

            //KAYDET
            RegisterDto superAdminRegister=modelMapperBeanClass.modelMapperMethod().map(registerEntity,RegisterDto.class);
            iRegisterServices.registerServiceCreate(superRoleId, superAdminRegister);
            System.out.println(superAdminRegister);
            System.out.println("super Admin User Eklendi");
        }
    }

    ///////////////////////////////////////////////////
    // ADMIN (2)
    private void admin() {
        synchronized (this) {
            // Dikkat: ROLE_  eklemelisiiiin.
            Long adminRoleId = iRoleRepository.save(RoleEntity.builder().roleId(2L).roleName(ERole.ADMIN.toString()).build()).getRoleId();

            //USER
            RegisterDto registerDto = new RegisterDto();
            registerDto.setRegisterNickname("admin");
            registerDto.setRegisterName("admin");
            registerDto.setRegisterSurname("admin");
            registerDto.setRegisterEmail("admin@gmail.com");
            registerDto.setRegisterPassword("Hm4444@.");

            //registerDto.setRoles(java.util.Collection<> );

            // USERDETAILS
            registerDto.setIsAccountNonLocked(true);
            registerDto.setIsCredentialsNonExpired(true);
            registerDto.setIsAccountNonExpired(true);
            registerDto.setIsEnabled(true);

            //KAYDET
            iRegisterServices.registerServiceCreate(adminRoleId, registerDto);
            System.out.println(registerDto);
            System.out.println("Admin Eklendi");
        } //end for
    }

    ///////////////////////////////////////////////////
    // WRITER (3)
    private void writer() {
        synchronized (this) {
            // Dikkat: ROLE_  eklemelisiiiin.
            Long writerRoleId = iRoleRepository.save(RoleEntity.builder().roleId(3L).roleName(ERole.WRITER.toString()).build()).getRoleId();

            for (long i = 1; i <= 2; i++) {
                //USER
                RegisterDto registerDto = new RegisterDto();
                registerDto.setRegisterNickname("writer" + i);
                registerDto.setRegisterName("writer" + i);
                registerDto.setRegisterSurname("writer" + i);
                registerDto.setRegisterEmail("writer" + i + "@gmail.com");
                registerDto.setRegisterPassword("Hm4444@.");

                //registerDto.setRoles(java.util.Collection<> );

                // USERDETAILS
                registerDto.setIsAccountNonLocked(true);
                registerDto.setIsCredentialsNonExpired(true);
                registerDto.setIsAccountNonExpired(true);
                registerDto.setIsEnabled(true);

                //KAYDET
                iRegisterServices.registerServiceCreate(writerRoleId, registerDto);
                System.out.println(registerDto);
                System.out.println("Writer Eklendi");
            } //end for
        }
    }

    ///////////////////////////////////////////////////
    // Role ve Register Eklemek (2)
    private void rolesAndRegisterCreate() {
        synchronized (this) {
            // Dikkat: ROLE_  eklemelisiiiin.
            Long userRoleId = iRoleRepository.save(RoleEntity.builder().roleId(4L).roleName(ERole.USER.toString()).build()).getRoleId();
            // Multithreading Senkron
            /* Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
            }
            });*/

            for (long i = 1; i <= 4; i++) {
                //USER
                RegisterDto registerDto = new RegisterDto();
                registerDto.setRegisterNickname("user" + i);
                registerDto.setRegisterName("user" + i);
                registerDto.setRegisterSurname("user" + i);
                registerDto.setRegisterEmail("user" + i + "@gmail.com");
                registerDto.setRegisterPassword("Hm4444@.");

                //registerDto.setRoles(java.util.Collection<> );

                // USERDETAILS
                registerDto.setIsAccountNonLocked(true);
                registerDto.setIsCredentialsNonExpired(true);
                registerDto.setIsAccountNonExpired(true);
                registerDto.setIsEnabled(true);

                //KAYDET
                iRegisterServices.registerServiceCreate(userRoleId, registerDto);
                System.out.println(registerDto);
                System.out.println("User Eklendi");
            } //end for
        }
    }

    ///////////////////////////////////////////////////
    // Command Line Runner
    @Override
    public void run(String... args) throws Exception {
        // Uygulama başladığında çalışmasını istediğimiz komutlar
        System.out.println("CommandLineRunner Çalıştı Sade");
        // Mail gelmesin diye kapattım
        //SUPER ADMIN
        //superAdmin();

        //ADMIN
        //admin();

        //WRITER
        //writer();

        //USER
        //rolesAndRegisterCreate();
        log.info("CommandLineRunner Çalıştı Sade");
    }

} //end class

