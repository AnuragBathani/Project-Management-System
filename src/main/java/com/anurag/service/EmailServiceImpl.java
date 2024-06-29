package com.anurag.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl  implements EmailService{


    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmailWithToken(String useremail, String link) throws MessagingException {

        MimeMessage mimeMessage=javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,"utf-8");

        String subject="Join Project Team Invitation";

        String text="Click here to join the team :" + link ;

        mimeMessageHelper.setSubject(subject);

        mimeMessageHelper.setText(text);
        mimeMessageHelper.setTo(useremail);

        try {
            javaMailSender.send(mimeMessage);
        }
        catch (MailSendException e){
            throw new MailSendException("Failed to send Mail");
        }



    }
}
