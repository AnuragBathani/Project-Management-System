package com.anurag.controller;


import com.anurag.model.Massage;
import com.anurag.request.CreateMassageRequest;
import com.anurag.service.MassageService;
import com.anurag.service.ProjectService;
import com.anurag.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/massages")
public class MassageController {

    @Autowired
    private MassageService massageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private WebSocketController webSocketController;

    @PostMapping("/send")
    public ResponseEntity<Massage> sendMassage(@RequestBody CreateMassageRequest req) throws Exception {
        Massage sendMassage = webSocketController.sendMassage(req);
        webSocketController.notifyClients("/topic/messages", sendMassage);
        return ResponseEntity.ok(sendMassage);
    }
    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Massage>> getMassageByChatId(@PathVariable Long projectId) throws Exception {
        List<Massage> massages = massageService.getMassageByProjectId(projectId);
        webSocketController.notifyClients("/topic/chat/" + projectId, massages); // Notify clients about the new massages
        return ResponseEntity.ok(massages);
    }
}
