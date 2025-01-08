package com.eventorganizer.vendorManagement.repository;

import com.eventorganizer.vendorManagement.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor,Long>
{
    List<Vendor> findByVendorServiceType(String serviceType);
    Optional<Vendor> findByUserId(Long userId);
}
