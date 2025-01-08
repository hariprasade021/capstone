import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Vendor } from '../../../../core/model/Vendor';
import { UserService } from '../../../../core/services/user-service/user.service';
import { VendorService } from '../../../../core/services/vendor-service/vendor.service';



@Component({
  selector: 'app-vendor-profile',
  imports:[CommonModule],
  templateUrl: './vendor-profile.component.html',
  styleUrls: ['./vendor-profile.component.css']
})
export class VendorProfileComponent implements OnInit {
  vendor: Vendor | null = null; // To store vendor details
  errorMessage: string | null = null;

  constructor(
    private vendorService: VendorService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.loadVendorDetails();
  }

  loadVendorDetails(): void {
    this.userService.getUserId().subscribe({
      next: (userId) => {
        if (userId !== null) {
          this.vendorService.getVendorByUserId(userId).subscribe({
            next: (vendor) => {
              this.vendor = vendor; // Assign the vendor data to the component variable
            },
            error: (err) => {
              console.error('Error fetching vendor', err);
              this.errorMessage = 'Failed to load vendor details. Please try again later.';
            },
          });
        } else {
          this.errorMessage = 'User ID not found.';
        }
      },
      error: (err) => {
        console.error('Error fetching user ID', err);
        this.errorMessage = 'Failed to load user details.';
      },
    });
  }
}
