package com.eventorganizer.apigateway.service;

import com.eventorganizer.apigateway.feign.UserFeignClient;
import com.eventorganizer.apigateway.model.User;
import com.eventorganizer.apigateway.userrepo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private UserFeignClient userFeignClient;

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    public User saveUser(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        User savedUser = repo.save(user);
        // Create the user in the User Microservice using Feign Client
        User createdUser = userFeignClient.createUser(savedUser);

        return createdUser;
//        return repo.save(user);
    }

    public User findByUserName(String username){
        return repo.findByUsername(username);
    }

    public boolean checkIfUsernameExists(String username)
    {
        return repo.findByUsername(username)!=null;
    }

    public int getIdByUsername(String username) {
        User user = repo.findByUsername(username); // Find user by username
        if (user != null) {
            return user.getId(); // Return the userId if user is found
        } else {
            return 0; // Return null if user doesn't exist
        }
    }
}

