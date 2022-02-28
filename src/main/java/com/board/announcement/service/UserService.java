package com.board.announcement.service;

import com.board.announcement.model.User;
import com.board.announcement.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findUserById(String userId) {
        return userRepository.findById(String.valueOf(new ObjectId(userId))).get();
    }

    public User saveUser(User newUser){
        User user = userRepository.save(newUser);
        return user;
    }

}
