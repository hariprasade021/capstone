package com.eventorganizer.apigateway.feign;

import com.eventorganizer.apigateway.model.User;
import com.eventorganizer.apigateway.model.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE") // Replace with actual user service URL
public interface UserFeignClient {

    @PostMapping("api/users") // Endpoint in user service to create a user
    User createUser(@RequestBody User user);
}