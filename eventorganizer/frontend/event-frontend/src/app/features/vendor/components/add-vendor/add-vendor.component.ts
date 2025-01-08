import { Component } from '@angular/core';
import { VendorService } from '../../../../core/services/vendor-service/vendor.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Vendor } from '../../../../core/model/Vendor';
// import { Vendor, PaymentStatus } from '../models/vendor.model';
// import { VendorService } from '../services/vendor.service';

@Component({
  selector: 'app-add-vendor',
  templateUrl: './add-vendor.component.html',
  imports:[CommonModule,FormsModule],
  styleUrls: ['./add-vendor.component.css']
})
export class AddVendorComponent {
  vendor: Vendor = {
    vendorCompanyName: '',
    vendorServiceType: '',
    vendorName: '',
    vendorPhone: '',
    vendorEmail: '',
    vendorAmount: 0,
  };

  constructor(private vendorService: VendorService) {}

  onSubmit() {
    this.vendorService.createVendor(this.vendor).subscribe({
      next: (newVendor) => {
        alert('Vendor created successfully!');
        // Reset form or navigate away
      },
      error: (err) => {
        console.error('Error creating vendor', err);
        alert('Failed to create vendor.');
      }
    });
  }
}
