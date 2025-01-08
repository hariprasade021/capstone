import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../features/auth/auth.service';

@Component({
  selector: 'app-vendor-header',
  imports: [RouterModule, FormsModule, CommonModule],
  templateUrl: './vendor-header.component.html',
  styleUrl: './vendor-header.component.css'
})
export class VendorHeaderComponent 
{
  dropdownOpen = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  toggleDropdown() {
    this.dropdownOpen = !this.dropdownOpen;
  }

  logout() {
    console.log('Logout method called');

    // Call logout method
    this.authService.logout();

    // Navigate to login page
    this.router.navigate(['/login']).then(
      success => console.log('Navigation to login successful'),
      error => console.error('Navigation failed', error)
    );
  }


}
