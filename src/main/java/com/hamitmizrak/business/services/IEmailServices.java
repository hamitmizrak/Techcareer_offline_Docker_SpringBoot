package com.hamitmizrak.business.services;

// NOT: interface için önemli bilgiler
// 1-) interface extends ile başka bir interface ekleyebilirsin. =>
// public interface IBlogGenericsServices extends IProfileHeaderApp

// 2-) interface abstract ekleyerek implements eden class bütün metotları eklemez. =>
// abstract public interface IBlogGenericsServices

// Generics
// D => Dto
// E => Entity
public interface IEmailServices<D,E>{

    // MODEL MAPPER
    D entityToDto(E e);
    E dtoToEntity(D d);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Maili öncelikle Database'e kaydetsin.
    public D mailDatabase(D d);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // EMAİL BASIC SEND
    public D blogSendBasicEmail(D d);

    // EMAİL ATTACHMENT SEND (image,word,text v.s)
    public D blogSendAttachmentMail(D d);
} //end interface
