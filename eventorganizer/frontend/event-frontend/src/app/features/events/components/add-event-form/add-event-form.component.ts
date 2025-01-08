import { Component, OnInit } from '@angular/core';
import { EventService } from '../../../../core/services/event-service/event.service';
import { UserService } from '../../../../core/services/user-service/user.service';
import { VendorService } from '../../../../core/services/vendor-service/vendor.service';
import { GuestService } from '../../../../core/services/guest-service/guest.service'; // Import the Guest service
import { IEvent, EventType, Status } from '../../../../core/model/Event';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Vendor } from '../../../../core/model/Vendor';
import { Guest } from '../../../../core/model/Guest';

@Component({
  selector: 'app-add-event-form',
  imports: [FormsModule, CommonModule],
  templateUrl: './add-event-form.component.html',
  styleUrls: ['./add-event-form.component.css'],
})
export class AddEventFormComponent implements OnInit {
  eventData: IEvent = {
    userId: null,
    name: '',
    title: '',
    location: '',
    description: '',
    estimatedExpense: null,
    actualExpense: null,
    startDate: '',
    endDate: '',
    type: EventType.CORPORATE,
    status: Status.PENDING,
    vendors: [], // Temporarily hold selected vendor IDs
    guests: [],  // Array of numbers
  };

  vendors: {
    id: number;
    name: string;
    budget: string | number;
  }[] = []; // List of vendors
  guests: { id: number; name: string }[] = []; // List of guests
  loading = true; // Loading state for vendors and guests
  loadingGuests = true; // Loading state for guests
  error: string | null = null; // Error state for vendors
  errorGuests: string | null = null; // Error state for guests

  constructor(
    private eventService: EventService,
    private userService: UserService,
    private vendorService: VendorService, // Inject VendorService
    private guestService: GuestService, // Inject GuestService
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetchUserId();
    this.fetchVendors();
    this.fetchGuests();
  }

  private fetchUserId(): void {
    const storedUserId = sessionStorage.getItem('userId');
    if (storedUserId) {
      this.eventData.userId = parseInt(storedUserId, 10);
    } else {
      this.userService.getUserId().subscribe({
        next: (userId) => {
          if (userId !== null) {
            this.eventData.userId = userId;
            sessionStorage.setItem('userId', userId.toString());
          } else {
            console.error('User ID not found.');
          }
        },
        error: (err) => console.error('Error fetching user ID:', err),
      });
    }
  }

  private fetchVendors(): void {
    this.vendorService.getAllVendors().subscribe({
      next: (data: Vendor[]) => {
        this.vendors = data.map((vendor: Vendor) => ({
          id: vendor.vendorId ?? 0, // Use 0 or another fallback value if vendorId is undefined
          name: vendor.vendorName,
          budget: vendor.vendorAmount,
        }));
        this.loading = false;
      },
      error: (err) => {
        console.error('Error fetching vendors:', err);
        this.error = 'Failed to load vendors. Please try again later.';
        this.loading = false;
      },
    });
  }

  private fetchGuests(): void {
    this.guestService.getAllGuests().subscribe({
      next: (data: Guest[]) => {
        this.guests = data.map((guest: Guest) => ({
          id: guest.id ?? 0,
          name: guest.name,
        }));
        this.loadingGuests = false;
      },
      error: (err) => {
        console.error('Error fetching guests:', err);
        this.errorGuests = 'Failed to load guests. Please try again later.';
        this.loadingGuests = false;
      },
    });
  }

  onSubmit(eventForm: NgForm): void {
    if (eventForm.valid) {
      const eventPayload: IEvent = {
        ...this.eventData,
        vendors: [], // Clear vendors in the event payload
        guests: this.eventData.guests, // Pass guests as they are
      };

      this.eventService.createEvent(eventPayload).subscribe({
        next: (response) => {
          if (response && typeof response.id === 'number') {
            alert('Event created successfully');

            // Request vendors for the created event
            const eventId = response.id;
            this.requestVendors(eventId);

            this.router.navigate(['/addEvent']);
          } else {
            console.error('Invalid event ID in response:', response);
          }
        },
        error: (error) => {
          console.error('Error occurred while creating event:', error);
        },
      });
    }
  }



  private requestVendors(eventId: number): void {
    this.eventData.vendors.forEach((vendorId) => {
      this.eventService.requestVendor(eventId, vendorId).subscribe({
        next: () => {
          console.log(`Vendor ${vendorId} requested for event ${eventId}`);
        },
        error: (error) => {
          console.error(
            `Error requesting vendor ${vendorId} for event ${eventId}:`,
            error
          );
        },
      });
    });
  }

  private sendInvitationToGuest(guestId: number): void {
    const eventName = this.eventData.name;
    const eventDate = `${this.eventData.startDate} to ${this.eventData.endDate}`;
    const eventLocation = this.eventData.location;

    this.guestService.sendInvitation(
      guestId,
      eventName,
      eventDate,
      eventLocation
    ).subscribe({
      next: () => {
        console.log(`Invitation sent successfully to guest ID ${guestId}`);
      },
      error: (error) => {
        console.error(`Failed to send invitation to guest ID ${guestId}:`, error);
      },
    });
  }

  toggleVendor(vendorId: number, event: Event): void {
    const checkbox = event.target as HTMLInputElement;
    if (checkbox.checked) {
      this.eventData.vendors.push(vendorId);
    } else {
      const index = this.eventData.vendors.indexOf(vendorId);
      if (index > -1) this.eventData.vendors.splice(index, 1);
    }
  }

  toggleGuest(guestId: number, event: Event): void {
    const isChecked = (event.target as HTMLInputElement).checked;
    if (isChecked) {
      this.eventData.guests.push(guestId);
    } else {
      this.eventData.guests = this.eventData.guests.filter((id) => id !== guestId);
    }
  }
}
