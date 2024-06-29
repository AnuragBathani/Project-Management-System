package com.anurag.controller;

import com.anurag.model.Chat;
import com.anurag.model.Massage;
import com.anurag.model.User;
import com.anurag.request.CreateMassageRequest;
import com.anurag.service.MassageService;
import com.anurag.service.ProjectService;
import com.anurag.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class WebSocketController {

    @Autowired
    private MassageService massageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/massage")
    @SendTo("/topic/messages")
    public Massage sendMassage(CreateMassageRequest req) throws Exception {
        User user = userService.findUserById(req.getSenderId());
        if (user == null) throw new Exception("The user not found with id " + req.getSenderId());

        Chat chat = projectService.getProjectById(req.getProjectId()).getChat();
        if (chat == null) throw new Exception("The chat is not found with this id");

        Massage sendMassage = massageService.sendMassage(req.getSenderId(), req.getProjectId(), req.getContent());
        return sendMassage;
    }

    @MessageMapping("/chat/{projectId}")
    @SendTo("/topic/chat/{projectId}")
    public List<Massage> getMassageByChatId(@PathVariable Long projectId) throws Exception {
        List<Massage> massages = massageService.getMassageByProjectId(projectId);
        return massages;
    }

    public void notifyClients(String destination, Massage message) {
        messagingTemplate.convertAndSend(destination, message);
    }
    public void notifyClients(String destination, List<Massage> messages) {
        messagingTemplate.convertAndSend(destination, messages);
    }

}

