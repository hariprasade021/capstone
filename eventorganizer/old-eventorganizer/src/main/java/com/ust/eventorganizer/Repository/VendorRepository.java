package com.ust.eventorganizer.Repository;

import com.ust.eventorganizer.model.Event;
import com.ust.eventorganizer.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor,Long>
{
    List<Vendor> findAllByEventsContaining(Event event);
    @Query("SELECT v.name AS name, v.price AS price, v.contactNumber AS contactNumber, v.email AS email " +
            "FROM Vendor v WHERE v.services = :service")
    List<VendorSummary> findVendorsByService(String service);
}
