package com.anurag.service;

import com.anurag.config.JwtProvider;
import com.anurag.model.User;
import com.anurag.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {

        String email= JwtProvider.getEmailfromToken(jwt);

        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {

        User user=userRepository.findByEmail(email);

        if(user == null)
            throw new Exception("user not found by email");

        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {

        Optional<User> optionalUser=userRepository.findById(userId);

        if(optionalUser.isEmpty())
            throw new Exception("user not found");

        return optionalUser.get();
    }

    @Override
    public User updateUsersProjectSize(User user, int number) {

        user.setProjectSize(user.getProjectSize()+number);
        return userRepository.save(user);
    }
}
