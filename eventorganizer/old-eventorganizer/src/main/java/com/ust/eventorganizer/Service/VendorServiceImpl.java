package com.ust.eventorganizer.Service;

import com.ust.eventorganizer.Repository.VendorRepository;

import com.ust.eventorganizer.Repository.VendorSummary;
import com.ust.eventorganizer.exception.EventNotFoundException;
import com.ust.eventorganizer.exception.GuestNotFoundException;
import com.ust.eventorganizer.exception.VendorNotFoundException;
import com.ust.eventorganizer.model.Event;
import com.ust.eventorganizer.model.Guest;
import com.ust.eventorganizer.model.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorServiceImpl implements VendorService
{
    @Autowired
    private VendorRepository vendorRepo;

    @Autowired
    private EventService eventService;

    @Override
    public Vendor saveVendor(Vendor vendor)
    {
        return vendorRepo.save(vendor);
    }

    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepo.findAll();
    }

    @Override
    public Vendor getVendorById(Long id) throws VendorNotFoundException {
        return vendorRepo.findById(id).orElseThrow(()-> new VendorNotFoundException("Vendor with ID: "+id+" is not found"));
    }

    @Override
    public void deleteVendor(Long id)
    {
        vendorRepo.deleteById(id);
    }



    public Vendor updateVendor(Long id, Vendor vendor) throws VendorNotFoundException {


        if(!vendorRepo.existsById(id))
        {
            throw new VendorNotFoundException("Vendor with ID: "+id+" not found");
        }

        Vendor vendorToUpdate = getVendorById(id);
        if(vendor.getName()!=null)
        {
            vendorToUpdate.setName(vendor.getName());
        }
        if(vendor.getLocation()!=null)
        {
            vendorToUpdate.setLocation(vendor.getLocation());
        }
        if(vendor.getServices()!=null)
        {
            vendorToUpdate.setServices(vendor.getServices());
        }
        if(vendor.getPrice()!=null)
        {
            vendorToUpdate.setPrice(vendor.getPrice());
        }
        if(vendor.getContactNumber()!=null)
        {
            vendorToUpdate.setContactNumber(vendor.getContactNumber());
        }
        if(vendor.getEmail()!=null)
        {
            vendorToUpdate.setEmail(vendor.getEmail());
        }


        return vendorRepo.save(vendorToUpdate);
    }

    @Override
    public List<Vendor> getAllVendorsForEvent(Long eventId) throws EventNotFoundException {
        // Fetch the event using the eventId
        Event event = eventService.getEventById(eventId);
        return vendorRepo.findAllByEventsContaining(event); // Use the method to get vendors
    }

    @Override
    public List<VendorSummary> getVendorsByService(String service)
    {
        return vendorRepo.findVendorsByService(service);
    }



}
