package com.ClientService.ClientService.feignClient;

import com.eventManagement.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="USER-SERVICE")
public interface UserFeign
{
    @GetMapping("api/users/{id}")
    public UserDTO getUserById(@PathVariable long userId);
}
