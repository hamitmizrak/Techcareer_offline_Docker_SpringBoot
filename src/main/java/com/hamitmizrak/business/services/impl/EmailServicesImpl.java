package com.hamitmizrak.business.services.impl;

import com.hamitmizrak.bean.ModelMapperBeanClass;
import com.hamitmizrak.business.dto.EmailDto;
import com.hamitmizrak.business.services.IEmailServices;
import com.hamitmizrak.data.entity.EmailEntity;
import com.hamitmizrak.data.repository.IEmailRepository;
import com.hamitmizrak.exception.HamitMizrakException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.CharEncoding;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

// Generics
// D => Dto
// E => Entity

// NOT: @Transaction Create, Delete, Update

// LOMBOK
@RequiredArgsConstructor
@Log4j2

// SERVICE
// Asıl iş Yükünü yapan yer
@Service
@Component("emailServicesImpl") //Spring'in bir parçasıdır.
public class EmailServicesImpl implements IEmailServices<EmailDto, EmailEntity> {

    // Injection (Lombok Constructor)
    private final ModelMapperBeanClass modelMapperBeanClass;
    private final IEmailRepository iEmailRepository;
    private final JavaMailSender javaMailSender; // Email Attachement için

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // MODEL MAPPER
    @Override
    public EmailDto entityToDto(EmailEntity emailEntity) {
        return modelMapperBeanClass.modelMapperMethod().map(emailEntity, EmailDto.class);
    }

    @Override
    public EmailEntity dtoToEntity(EmailDto emailDto) {
        return modelMapperBeanClass.modelMapperMethod().map(emailDto, EmailEntity.class);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // DATABASE SAVE
    @Override
    public EmailDto mailDatabase(EmailDto emailDto) {
        // MODEL MAPPER, SAVE
        EmailEntity emailEntity = dtoToEntity(emailDto);
        emailEntity = iEmailRepository.save(emailEntity);
        emailDto.setId(emailEntity.getId());
        return emailDto;
    }

    //////////////////////////////////////////////////////////
    // EMAİL BASIC SEND (attachment yani ek bir dosya olmadan mail göndermek)
    @Override
    @Transactional
    public EmailDto blogSendBasicEmail(EmailDto emailDto) {
        // Bu Email'i öncelikle Database'e kaydetmelisin.
        EmailDto mailBasicSaveDatabase = mailDatabase(emailDto);

        // database kaydetmeden Mail göndermesin
        if (mailBasicSaveDatabase.getId() != null) {
            // Daha sonra bunu mail olarak göndermelisin. (Mail Send)
            // org.springframework.mail.SimpleMailMessage
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(emailDto.getEmailFrom()); // MAİL KİMDEN
            simpleMailMessage.setTo(emailDto.getEmailTo()); // MAİL KİME
            simpleMailMessage.setSubject(emailDto.getEmailSubject()); // MAİL SUBJECT
            simpleMailMessage.setText(emailDto.getEmailText()); // MAİL TEXT
            simpleMailMessage.setCc(emailDto.getEmailFrom()); //CC
            /*
            simpleMailMessage.setCc(emailDto.getEmailCc()); //CC
            simpleMailMessage.setBcc(emailDto.getEmailBcc()); //BCC
            */

            // MAİL DATE
            simpleMailMessage.setSentDate(emailDto.getSentDate());

            //Mail Gönder
            javaMailSender.send(simpleMailMessage);
        } else {
            throw new HamitMizrakException("Database kaydedilmedi bundan dolayı mail gönderilmedi");
        }
        return emailDto;
    }

    //////////////////////////////////////////////////////////
    // EMAİL ATTACHMENT SEND (image,word,text v.s)
    @Override
    @Transactional
    @SneakyThrows // for Exception (Lombok)
    public EmailDto blogSendAttachmentMail(EmailDto emailDto) {

        // Bu Email'i öncelikle Database'e kaydetmelisin.
        EmailDto mailAttachmentSaveDatabase = mailDatabase(emailDto);

        // database kaydetmeden Mail göndermesin
        if (mailAttachmentSaveDatabase.getId() != null) {
            // Daha sonra bunu mail olarak göndermelisin.
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            // MimeMessageHelper(mimeMessage, true, CharEncoding.UTF_8) mimeMessage:mesaj true:attachment , UTF8:dil
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, CharEncoding.UTF_8);

            // Content
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<b>Merhabalar</b>,<br>");
            stringBuilder.append("<mark><i>Please look at this nice picture:.</i></mark>,<br>");
            stringBuilder.append("<img src='cid:image001'/><br><b>Best Regards</b>");
            String content = stringBuilder.toString();
            try {
                messageHelper.setFrom(emailDto.getEmailFrom());
                messageHelper.setTo(emailDto.getEmailTo());

                messageHelper.setSubject(emailDto.getEmailSubject());
                // burdaki true eğer Html formatında yazmak istersen kullanabilirsin
                messageHelper.setText(content, true);
                messageHelper.setBcc(emailDto.getEmailTo());
                messageHelper.setBcc(emailDto.getEmailTo());
                messageHelper.setSentDate(emailDto.getSentDate());

                //pdf txt resim göndermek
                FileSystemResource pdf = new FileSystemResource(new File("C:\\fileio\\document.pdf"));
                messageHelper.addAttachment("document.pdf", pdf);
                messageHelper.addAttachment("deneme.txt", new FileSystemResource(new File("C:\\fileio\\deneme.txt")));
                FileSystemResource resim = new FileSystemResource(new File("C:\\fileio\\document.pdf"));
                messageHelper.addAttachment("picture.png", resim);
                messageHelper.addInline("image001", resim);
            } catch (MessagingException e) {
                e.printStackTrace();
                throw new HamitMizrakException("Gönderim Sırasında hata meydana geldi.");
            }

            //Mail Gönder
            javaMailSender.send(mimeMessage);
            return emailDto;
        } else
            throw new HamitMizrakException("Database kaydedilmedi bundan dolayı mail gönderilmedi");
    } // end blogSendAttachmentMail
}//end class
