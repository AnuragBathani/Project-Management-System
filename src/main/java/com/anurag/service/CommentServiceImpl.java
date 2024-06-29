package com.anurag.service;

import com.anurag.model.Comment;
import com.anurag.model.Issue;
import com.anurag.model.User;
import com.anurag.repository.CommentRepository;
import com.anurag.repository.IssueRepository;
import com.anurag.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private UserRepository userRepository;



    @Override
    public Comment createComment(Long issueid, Long userid, String content) throws Exception {
        Optional<Issue> issueOptional=issueRepository.findById(issueid);
        Optional<User> userOptional=userRepository.findById(userid);

        if(issueOptional.isEmpty()){
            throw new Exception("issue not found with this issue id" + issueid);

        }
        if(userOptional.isEmpty()){
            throw new Exception("user not found with this userid "+ userid);
        }


        Issue  issue=issueOptional.get();
        User user=userOptional.get();

            Comment comment=new Comment();

            comment.setIssue(issue);
            comment.setUser(user);
            comment.setLocalDateTime(LocalDateTime.now());
            comment.setContent(content);
            Comment savedcomment=commentRepository.save(comment);

            issue.getComments().add(savedcomment);


        return savedcomment;
    }

    @Override
    public void deletaComment(Long commentid, Long userid) throws Exception {
        Optional<Comment> commentOptional=commentRepository.findById(commentid);
        Optional<User> userOptional=userRepository.findById(userid);

        if(commentOptional.isEmpty()){
            throw new Exception("comment not found with this comment id" + commentid);

        }
        if(userOptional.isEmpty()){
            throw new Exception("user not found with this userid "+ userid);
        }


        Comment  comment=commentOptional.get();
        User user=userOptional.get();



        if(comment.getUser().equals(user)){
            commentRepository.delete(comment);
        }
        else {
            throw new Exception("User does not have permission to delete the comment");
        }


    }

    @Override
    public List<Comment> findCommentByIssueId(Long issueId) {
        return commentRepository.findByIssueId(issueId);
    }
}
