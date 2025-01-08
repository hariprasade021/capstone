import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { Router,ActivatedRoute } from '@angular/router'; // Import Router for navigation
import { AuthService } from '../../../features/auth/auth.service';

@Component({
  selector: 'app-dashboard-header',
  imports: [RouterModule, FormsModule, CommonModule],
  templateUrl: './dashboard-header.component.html',
  styleUrl: './dashboard-header.component.css'
})
export class DashboardHeaderComponent {
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
