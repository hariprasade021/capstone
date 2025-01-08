package com.eventManagement.repository;

import com.eventManagement.model.VendorRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorRequestRepository extends JpaRepository<VendorRequest,Long>
{
    List<VendorRequest> findByVendorIdAndStatus(int vendorId, VendorRequest.RequestStatus status);
    List<VendorRequest> findByEventIdAndStatus(int eventId, VendorRequest.RequestStatus status);
    boolean existsByEventIdAndVendorId(Long eventId, Long vendorId);
}
