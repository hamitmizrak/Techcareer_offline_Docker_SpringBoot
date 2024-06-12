package com.hamitmizrak.business.services.impl;

import com.hamitmizrak.business.services.IForRegisterTokenEmailConfirmationServices;
import com.hamitmizrak.data.entity.ForRegisterTokenEmailConfirmationEntity;
import com.hamitmizrak.data.repository.IForRegisterTokenEmailConfirmationEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// 4 0 3 0
// LOMBOK
@Data
@RequiredArgsConstructor // Injection

@Transactional
@Service
public class ForRegisterTokenEmailConfirmationServicesImpl implements IForRegisterTokenEmailConfirmationServices<ForRegisterTokenEmailConfirmationEntity> {

    // INJECTION
    private final IForRegisterTokenEmailConfirmationEntity iTokenRepository;

    // CREATE TOKEN
    // Mailine gelen linke tıkladığımda çalışacak metot
    @Override
    public String createToken(ForRegisterTokenEmailConfirmationEntity tokenConfirmationEntity) {
        ForRegisterTokenEmailConfirmationEntity tokenConfirmationEntity1=iTokenRepository.save(tokenConfirmationEntity);
        return tokenConfirmationEntity1.getToken();
    }

    // DELETE TOKEN
    // Mailine gelen linke tıkladığımda token silinecek metot
    @Override
    public void deleteToken(Long id) {
         iTokenRepository.deleteById(id);
    }
} //end class
