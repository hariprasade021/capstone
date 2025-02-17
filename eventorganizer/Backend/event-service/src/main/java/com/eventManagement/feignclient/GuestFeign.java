package com.eventManagement.feignclient;


import com.eventManagement.dto.GuestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "GUEST-SERVICE")
public interface GuestFeign {

    @GetMapping("api/guest/{id}")
    GuestDTO getGuestById(@PathVariable Long id);



}
