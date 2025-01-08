package com.eventManagement.feignclient;


import com.eventManagement.dto.EventDTO;
import com.eventManagement.dto.VendorDTO;
import com.eventManagement.dto.VendorRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = "VENDOR-SERVICE")
public interface VendorFeign
{
    @GetMapping("api/vendors/{id}")
    VendorDTO getVendorById(@PathVariable Long id);

//    @PostMapping("request/vendor/{vendorId}/requests")
//    public ResponseEntity<List<VendorRequestDTO>> viewRequests(@PathVariable Long vendorId);

    @PostMapping("api/vendors/create/{eventId}/request/{vendorId}")
    public VendorRequestDTO createEventRequest(@RequestParam Long eventId, @RequestParam Long vendorId);


}

