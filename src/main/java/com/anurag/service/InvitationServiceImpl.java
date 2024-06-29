package com.anurag.service;

import com.anurag.model.Invitation;
import com.anurag.repository.InvitationRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService{

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private EmailService emailService;
    @Override
    public void sendInvitaiton(String email, Long projectId) throws MessagingException {

        String invitationToken= UUID.randomUUID().toString();


        Invitation invitation=new Invitation();

        invitation.setEmail(email);
        invitation.setProjectId(projectId);
        invitation.setToken(invitationToken);

        invitationRepository.save(invitation);

        String invitationlink="http://localhost:5173/accept_invitation?token="+invitationToken;

        emailService.sendEmailWithToken(email,invitationlink);
    }

    @Override
    public Invitation acceptInvitation(String token, Long userId) throws Exception {

        Invitation invitation=invitationRepository.findByToken(token);

        if(invitation == null){
            throw new Exception("Invalid Invitation token");
        }
        return invitation;
    }

    @Override
    public String getTokenByUsermail(String email) {
        Invitation invitation=invitationRepository.findByEmail(email);
        return invitation.getToken();
    }

    @Override
    public void deleteToken(String token) {
        Invitation invitation=invitationRepository.findByToken(token);
        invitationRepository.delete(invitation);

    }
}
