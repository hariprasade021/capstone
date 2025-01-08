import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Guest } from '../../../../core/model/Guest';
import { GuestService } from '../../../../core/services/guest-service/guest.service';

@Component({
  selector: 'app-display-guest',
  imports: [CommonModule],
  templateUrl: './view-guests.component.html',
  styleUrl: './view-guests.component.css'
})
export class DisplayGuestComponent {
  guests: Guest[] = []; // Array to hold the fetched guests
  loading: boolean = true; // Loading indicator
  error: string | null = null; // Error message

  constructor(private guestService: GuestService) {}

  ngOnInit(): void {
    this.fetchGuests();
  }

  fetchGuests(): void {
    this.guestService.getAllGuests().subscribe({
      next: (data) => {
        this.guests = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error fetching guests:', err);
        this.error = 'Failed to load guests. Please try again later.';
        this.loading = false;
      },
    });
  }
  
}
