package com.anurag.controller;

import com.anurag.model.*;
import com.anurag.response.MassageResponse;
import com.anurag.service.InvitationService;
import com.anurag.service.ProjectService;
import com.anurag.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    @Autowired
    private UserService userService;

    @Autowired
    private InvitationService invitationService;


    @GetMapping
    public ResponseEntity<List<Project>> getprojects(

            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tags,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        User user=userService.findUserProfileByJwt(jwt);
        List<Project> projects=projectService.getProjectByTeam(user,category,tags);

        return new ResponseEntity<>(projects, HttpStatus.OK);

    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(

            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        User user=userService.findUserProfileByJwt(jwt);
        Project project=projectService.getProjectById(projectId);

        return new ResponseEntity<>(project, HttpStatus.OK);

    }


  @PostMapping
    public ResponseEntity<Project> createProject(

            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception{

        User user=userService.findUserProfileByJwt(jwt);
       Project createdproject=projectService.createProject(project,user);
        return new ResponseEntity<>(createdproject, HttpStatus.OK);

    }


    @PatchMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception{

        User user=userService.findUserProfileByJwt(jwt);
        Project updatedProject=projectService.updateProject(project,projectId);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MassageResponse> deleteProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        User user=userService.findUserProfileByJwt(jwt);
        projectService.deleateProject(projectId,user.getId());

        MassageResponse response=new MassageResponse("project deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchprojects(

            @RequestParam(required = false) String keyword,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        User user=userService.findUserProfileByJwt(jwt);
        List<Project> projects=projectService.searchProjects(keyword,user);

        return new ResponseEntity<>(projects, HttpStatus.OK);

    }

    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat> getChatByProjectId(

            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        User user=userService.findUserProfileByJwt(jwt);
        Chat chat=projectService.getChatByProjectId(projectId);

        return new ResponseEntity<>(chat, HttpStatus.OK);

    }

    @PostMapping("/invite")
    public ResponseEntity<MassageResponse> inviteProject(
            @RequestBody InvitationRequest req,
            @RequestHeader("Authorization") String jwt

            ) throws Exception{

        User user=userService.findUserProfileByJwt(jwt);
       invitationService.sendInvitaiton(req.getEmail(), req.getProjectId());

       MassageResponse msg=new MassageResponse("User Invitation sent");
        return new ResponseEntity<>(msg, HttpStatus.OK);

    }


    @PostMapping("/accept_invitation")
    public ResponseEntity<Invitation> acceptInviteProject(
            @RequestParam String token,
            @RequestBody InvitationRequest req,
            @RequestHeader("Authorization") String jwt

    ) throws Exception{

        User user=userService.findUserProfileByJwt(jwt);
       Invitation invitation= invitationService.acceptInvitation(token, user.getId());

        projectService.addUserToProject(invitation.getProjectId(),user.getId());


        return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED);

    }








}
