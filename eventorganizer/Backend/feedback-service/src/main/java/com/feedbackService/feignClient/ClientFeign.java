package com.feedbackService.feignClient;

import com.feedbackService.dto.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CLIENTSERVICE")
public interface ClientFeign{
    @GetMapping("api/client/{clientId}")
    ClientDTO getClientById(@PathVariable Long clientId);
}