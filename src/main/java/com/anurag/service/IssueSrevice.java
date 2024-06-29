package com.anurag.service;

import com.anurag.model.Issue;
import com.anurag.model.User;
import com.anurag.request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueSrevice {

    Issue getIssueById(Long issueId) throws Exception;

    List<Issue>  getIssueByProjectId(Long  projectId) throws Exception;

    Issue createissue(IssueRequest issueRequest, User user) throws  Exception;

//    Optional<Issue> updateIssue(Long issueid,IssueRequest updatedissue,Long userId) throws Exception;

    void deleteissue(Long issueId,Long userid)throws Exception;

    Issue addusertoIssue(Long issueid,Long userid) throws Exception;

    Issue updatestatus(Long issueid,String status) throws Exception;

}
