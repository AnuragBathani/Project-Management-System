package com.anurag.controller;

import com.anurag.model.Comment;
import com.anurag.model.User;
import com.anurag.request.createCommentRequest;
import com.anurag.response.MassageResponse;
import com.anurag.service.CommentService;
import com.anurag.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

        @Autowired
        private CommentService commentService;

        @Autowired
        private UserService userService;

        @PostMapping
        public ResponseEntity<Comment> createComment(
                @RequestBody createCommentRequest req,
                @RequestHeader("Authorization") String jwt
        ) throws Exception{

                User user= userService.findUserProfileByJwt(jwt);
                Comment createComment=commentService.createComment(req.getIssueId(), user.getId(), req.getContent());
                return new ResponseEntity<>(createComment, HttpStatus.OK);
        }

        @DeleteMapping("/{commentId}")
        public ResponseEntity<MassageResponse> deleteComment(
                @PathVariable Long commentId,
                @RequestHeader("Authorization") String jwt
        ) throws Exception{

                User user= userService.findUserProfileByJwt(jwt);
                commentService.deletaComment(commentId, user.getId());
                MassageResponse response=new MassageResponse();

                response.setMassage("comment deleted Successfully");
                return new ResponseEntity<>(response,HttpStatus.OK);
        }


        @GetMapping("/{issueId}")
        public ResponseEntity<List<Comment>> getCommentByIssueId(
                @PathVariable Long issueId

        ) throws Exception{

              List<Comment> comments=commentService.findCommentByIssueId(issueId);

              return new ResponseEntity<>(comments,HttpStatus.OK);
        }






}
