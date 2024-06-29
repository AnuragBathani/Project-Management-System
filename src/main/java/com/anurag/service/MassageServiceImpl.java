package com.anurag.service;

import com.anurag.model.Chat;
import com.anurag.model.Massage;
import com.anurag.model.User;
import com.anurag.repository.MassageRepository;
import com.anurag.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MassageServiceImpl implements MassageService{

    @Autowired
    private MassageRepository massageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectService projectService;


    @Override
    public Massage sendMassage(Long senderId, Long projectId, String content) throws Exception {

        User sender=userRepository.findById(senderId).orElseThrow(() -> new Exception("User not found with this id "+ senderId));

        Chat chat=projectService.getProjectById(projectId).getChat();

        Massage massage=new Massage();

        massage.setContent(content);
        massage.setSender(sender);
        massage.setCreatedAt(LocalDateTime.now());
        massage.setChat(chat);
        Massage savedmassage=massageRepository.save(massage);
        chat.getMassagese().add(massage);
        return savedmassage ;
    }

    @Override
    public List<Massage> getMassageByProjectId(Long projectId) throws Exception {

        Chat chat=projectService.getProjectById(projectId).getChat();
        List<Massage> massages=massageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
        return massages;
    }
}
