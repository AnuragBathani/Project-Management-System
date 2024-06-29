package com.anurag.controller;

import com.anurag.DTO.IssueDTO;
import com.anurag.model.Issue;
import com.anurag.model.User;
import com.anurag.request.IssueRequest;
import com.anurag.response.MassageResponse;
import com.anurag.service.IssueSrevice;
import com.anurag.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueSrevice issueSrevice;

    @Autowired
    private UserService userService;


    @GetMapping("/{issueid}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long issueid) throws Exception{

        return ResponseEntity.ok(issueSrevice.getIssueById(issueid));
    }

    @GetMapping("/project/{projectid}")
    public ResponseEntity<List<Issue>> getIssueByProjectId(@PathVariable Long projectid) throws Exception{

        return ResponseEntity.ok(issueSrevice.getIssueByProjectId(projectid));
    }

   @PostMapping
    public ResponseEntity<IssueDTO  > createIssue(@RequestBody IssueRequest issueRequest,
                                                  @RequestHeader("Authorization") String token) throws Exception{

        User tokenUser=userService.findUserProfileByJwt(token);
        User user=userService.findUserById(tokenUser.getId());



            Issue createIssue=issueSrevice.createissue(issueRequest,tokenUser);
            IssueDTO dto=new IssueDTO();

            dto.setDiscription(createIssue.getDiscription());
            dto.setDueDate(createIssue.getDueDate());
            dto.setId(createIssue.getId());
            dto.setPriority(createIssue.getPriority());
            dto.setProject(createIssue.getProject());
            dto.setProjectID(createIssue.getProjectID());
            dto.setStatus(createIssue.getStatus());
            dto.setTitle(createIssue.getTitle());
            dto.setTags(createIssue.getTags());
            dto.setAssignee(createIssue.getAssignee());

            return ResponseEntity.ok(dto);

    }


    @DeleteMapping("/{issueid}")
    public ResponseEntity<MassageResponse> deleteIssue(@PathVariable Long issueid,
                                                                @RequestHeader("Authorization") String token) throws Exception{

        User tokenUser=userService.findUserProfileByJwt(token);
        issueSrevice.deleteissue(issueid,tokenUser.getId());

        MassageResponse response=new MassageResponse();

        response.setMassage("Issue Deleted successfully");

        return ResponseEntity.ok(response);

    }

    @PutMapping("/{issueid}/assignee/{userid}")
    public ResponseEntity<Issue> addUserToIssue(@PathVariable Long issueid,
                                                @PathVariable Long userid) throws Exception{

        Issue issue=issueSrevice.addusertoIssue(issueid,userid);


        return ResponseEntity.ok(issue);
    }


    @PutMapping("/{issueid}/status/{status}")
    public ResponseEntity<Issue> updateIssueStatus(@PathVariable String status,
                                                   @PathVariable Long issueid) throws Exception{

        Issue issue=issueSrevice.updatestatus(issueid,status);


        return ResponseEntity.ok(issue);
    }








}
