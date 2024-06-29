package com.anurag.service;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendEmailWithToken(String useremail,String link) throws MessagingException;
}
