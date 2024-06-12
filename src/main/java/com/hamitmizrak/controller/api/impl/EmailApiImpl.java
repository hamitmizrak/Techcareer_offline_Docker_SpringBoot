package com.hamitmizrak.controller.api.impl;

import com.hamitmizrak.business.dto.EmailDto;
import com.hamitmizrak.business.services.IEmailServices;
import com.hamitmizrak.controller.api.IEmailApi;
import com.hamitmizrak.utils.FrontendPortUrl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// LOMBOK
@RequiredArgsConstructor

// REST
@RestController
@CrossOrigin(origins = FrontendPortUrl.REACT_FRONTEND_PORT_URL)
@RequestMapping("/email/api/v1")
public class EmailApiImpl implements IEmailApi<EmailDto> {

    // INJECTION
    private final IEmailServices iEmailServices;

    // http://localhost:4444/email/api/v1/basic/email
    @Override
    @PostMapping("/basic/email")
    //@PreAuthorize("hasPermission(#article, 'isEditor')")
    public EmailDto blogSendEmail(@Valid @RequestBody EmailDto emailDto) {
        return (EmailDto) iEmailServices.blogSendBasicEmail(emailDto);
    }

    // http://localhost:4444/email/api/v1/attachment/email
    @Override
    @PostMapping("/attachment/email")
    public EmailDto blogSendAttachmentMail(@Valid @RequestBody EmailDto emailDto) {
        return (EmailDto) iEmailServices.blogSendAttachmentMail (emailDto);
    }
} //end class
