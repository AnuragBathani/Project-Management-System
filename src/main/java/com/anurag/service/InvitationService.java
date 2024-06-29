package com.anurag.service;

import com.anurag.model.Invitation;
import jakarta.mail.MessagingException;

public interface InvitationService {

    public void sendInvitaiton(String email,Long projectId) throws MessagingException;

    public Invitation acceptInvitation(String token,Long userId) throws Exception;

    public String getTokenByUsermail(String  email);

    public  void deleteToken(String token);
}
