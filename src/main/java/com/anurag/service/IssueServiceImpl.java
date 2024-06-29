package com.anurag.service;

import com.anurag.model.Issue;
import com.anurag.model.Project;
import com.anurag.model.User;
import com.anurag.repository.IssueRepository;
import com.anurag.request.IssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class IssueServiceImpl implements IssueSrevice{

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;


    @Override
    public Issue getIssueById(Long issueId) throws Exception {

        Optional<Issue> issue=issueRepository.findById(issueId);
        if(issue.isPresent()){
            return issue.get();
        }

        throw new Exception("No issue found with issueid " + issueId);
    }

    @Override
    public List<Issue> getIssueByProjectId(Long projectId) throws Exception {
        return issueRepository.findByProjectID(projectId);
    }

    @Override
    public Issue createissue(IssueRequest issueRequest, User user) throws Exception {

        Project project=projectService.getProjectById(issueRequest.getProjectID());

        Issue issue=new Issue();

        issue.setTitle(issueRequest.getTitle());
        issue.setDiscription(issueRequest.getDiscription());
        issue.setStatus(issueRequest.getStatus());
        issue.setProjectID(issueRequest.getProjectID());
        issue.setPriority(issueRequest.getPriority());
        issue.setDueDate(issueRequest.getDueDate());
        issue.setProject(project);


        return issueRepository.save(issue);
    }

    @Override
    public void deleteissue(Long issueId, Long userid) throws Exception {
        getIssueById(issueId);
        issueRepository.deleteById(issueId);
    }

    @Override
    public Issue addusertoIssue(Long issueid, Long userid) throws Exception {

        User user=userService.findUserById(userid);

        Issue issue=getIssueById(issueid);

        issue.setAssignee(user);
        return issueRepository.save(issue);
    }

    @Override
    public Issue updatestatus(Long issueid, String status) throws Exception {

       Issue issue=getIssueById(issueid);

       issue.setStatus(status);
        return issueRepository.save(issue);
    }
}
