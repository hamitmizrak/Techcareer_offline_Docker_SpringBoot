package com.hamitmizrak.controller.api;

import com.hamitmizrak.business.dto.EmailDto;

// INTERFACE (IEmailApi)
// D: Dto

public interface IEmailApi <D>{

    EmailDto blogSendEmail(EmailDto emailDto);

    EmailDto blogSendAttachmentMail( EmailDto emailDto);
}
