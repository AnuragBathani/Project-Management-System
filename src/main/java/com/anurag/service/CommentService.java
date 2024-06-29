package com.anurag.service;

import com.anurag.model.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(Long issueid,Long userid,String content) throws Exception;

    void deletaComment(Long commentid,Long userid) throws Exception;

    List<Comment> findCommentByIssueId(Long issueId);
}
