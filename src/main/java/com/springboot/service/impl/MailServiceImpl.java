package com.springboot.service.impl;

import com.springboot.config.email.MailConfig;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@Service
public class MailServiceImpl {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    MailConfig mailConfig;
    @Autowired
    JavaMailSenderImpl javaMailSender;



    public MimeMessage createMessage(String subject,String content,String sentTo){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            mimeMessage.addHeader("Content-Type","text/plain;charset='UTF-8'");
            mimeMessage.addHeader("X-Mailer","Microsoft Outlook Express 6.00.2900.2869");
            mimeMessage.setFrom(new InternetAddress(mailConfig.getUserName()));
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO,sentTo);
            mimeMessage.setSubject(subject);
            mimeMessage.setText(content);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return mimeMessage;

    }






}
