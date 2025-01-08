package com.ust.eventorganizer.Service;

import com.ust.eventorganizer.Repository.VendorSummary;
import com.ust.eventorganizer.exception.EventNotFoundException;
import com.ust.eventorganizer.exception.VendorNotFoundException;
import com.ust.eventorganizer.model.Vendor;

import java.util.List;

public interface VendorService
{
    public Vendor saveVendor(Vendor vendor);
    public List<Vendor> getAllVendors();
    public Vendor getVendorById(Long id) throws VendorNotFoundException;
    public void deleteVendor(Long id);
    public Vendor updateVendor(Long id, Vendor vendor) throws VendorNotFoundException;
    List<Vendor> getAllVendorsForEvent(Long eventId) throws EventNotFoundException;
    public List<VendorSummary> getVendorsByService(String service);
}
