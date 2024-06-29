package com.anurag.controller;

import com.anurag.config.JwtProvider;
import com.anurag.model.User;
import com.anurag.repository.UserRepository;
import com.anurag.request.LoginRequest;
import com.anurag.response.AuthResponse;
import com.anurag.service.CustomeUserDetailsImpl;
import com.anurag.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomeUserDetailsImpl customeUserDetails;


    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createuserhandler(@RequestBody User user) throws Exception {

        User isuserExist=userRepository.findByEmail(user.getEmail());

        if(isuserExist != null){
            throw new Exception("email already found with diffrent account");
        }

        User createdUser=new User();

        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createdUser.setEmail(user.getEmail());
        createdUser.setFullname(user.getFullname());

        User saveduser=userRepository.save(createdUser);

        subscriptionService.createSubsription(saveduser);

        Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwttoken= JwtProvider.genereteToken(authentication);

        AuthResponse res=new AuthResponse();
        res.setJwt(jwttoken);
        res.setMessage("signup success");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> signing(@RequestBody LoginRequest loginRequest){

            String username=loginRequest.getEmail();
            String password=loginRequest.getPassword();

        Authentication authentication=authenticate(username,password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwttoken= JwtProvider.genereteToken(authentication);

        AuthResponse res=new AuthResponse();
        res.setJwt(jwttoken);
        res.setMessage("signin success");

        return new ResponseEntity<>(res, HttpStatus.CREATED);


    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails=customeUserDetails.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException("invalid username");
        }

        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    }
}
