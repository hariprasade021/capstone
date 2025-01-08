import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Vendor } from '../../../../core/model/Vendor';
import { VendorService } from '../../../../core/services/vendor-service/vendor.service';

@Component({
  selector: 'app-display-vendor',
  imports: [CommonModule],
  templateUrl: './view-vendors.component.html',
  styleUrl: './view-vendors.component.css'
})
export class DisplayVendorComponent {
  vendors: Vendor[] = []; // Array to hold the fetched vendors
  loading: boolean = true; // Loading indicator
  error: string | null = null; // Error message

  constructor(private vendorService: VendorService) {}

  ngOnInit(): void {
    this.fetchVendors();
  }

  fetchVendors(): void {
    this.vendorService.getAllVendors().subscribe({
      next: (data) => {
        this.vendors = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error fetching vendors:', err);
        this.error = 'Failed to load vendors. Please try again later.';
        this.loading = false;
      },
    });
  }
}
