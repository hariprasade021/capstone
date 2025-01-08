package com.ust.eventorganizer.Controller;

import com.ust.eventorganizer.Repository.VendorSummary;
import com.ust.eventorganizer.Service.VendorService;
import com.ust.eventorganizer.exception.VendorNotFoundException;
import com.ust.eventorganizer.model.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vendor")
public class VendorController
{
    @Autowired
    private VendorService vendorService;

    @PostMapping
    public Vendor createVendor(@RequestBody Vendor vendor) {
        return vendorService.saveVendor(vendor);
    }


    @GetMapping
    public List<Vendor> getAllVendor() {
        return vendorService.getAllVendors();
    }


    @GetMapping("/{id}")
    public Vendor getVendorById(@PathVariable Long id) throws VendorNotFoundException {
        return vendorService.getVendorById(id);
    }

    @PutMapping("/{id}/update")
    public Vendor updateVendor(@PathVariable Long id, @RequestBody Vendor vendor) throws VendorNotFoundException {
        return vendorService.updateVendor(id, vendor);
    }


    @DeleteMapping("/{id}/delete")
    public void deleteEvent(@PathVariable Long id) {
        vendorService.deleteVendor(id);
    }

    @GetMapping("/vendors/by-service")
    public List<VendorSummary> getVendorsByService(@RequestParam String service) {
        return vendorService.getVendorsByService(service);
    }


}
