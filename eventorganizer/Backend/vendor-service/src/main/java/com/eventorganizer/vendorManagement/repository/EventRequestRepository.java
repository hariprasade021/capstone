package com.eventorganizer.vendorManagement.repository;

import com.eventorganizer.vendorManagement.model.EventRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRequestRepository extends JpaRepository<EventRequest, Long> {
    List<EventRequest> findByVendorIdAndStatus(Long vendorId, EventRequest.RequestStatus requestStatus);
}
