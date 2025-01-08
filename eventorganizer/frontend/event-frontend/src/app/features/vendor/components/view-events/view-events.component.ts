import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IEvent } from '../../../../core/model/Event';
import { VendorService } from '../../../../core/services/vendor-service/vendor.service';
import { EventService } from '../../../../core/services/event-service/event.service';
import { Observable, forkJoin } from 'rxjs';

@Component({
  selector: 'app-view-events',
  templateUrl: './view-events.component.html',
  styleUrls: ['./view-events.component.css'],
  imports: [CommonModule]
})
export class ViewEventsComponent implements OnInit {
  acceptedEvents$: Observable<number[]> | undefined; // Observable for accepted event IDs
  eventDetails$: Observable<IEvent[]> | undefined; // Holds observable of detailed events
  errorMessage: string | null = null; // Error message for user-friendly feedback
  isLoading: boolean = true; // Loading state for better UX

  constructor(
    private vendorService: VendorService,
    private eventService: EventService,
    private cdRef: ChangeDetectorRef
  ) {}

  ngOnInit(): void
  {
    const userId = this.getCurrentUserId(); // Replace with actual logic to retrieve the user ID

    if (userId) {
      this.vendorService.getVendorByUserId(userId).subscribe({
        next: (vendor) => {
          const id = vendor.vendorId;
          console.log('Vendor retrieved:', vendor); // Debugging vendor details

          // Ensure vendorId is valid before calling getAcceptedEvents
          if (id != null) {
            this.fetchAcceptedEvents(id);
          } else {
            this.errorMessage = 'Vendor ID is undefined or null.';
            this.isLoading = false;
          }
        },
        error: (err) => {
          console.error('Error fetching vendor by user ID:', err);
          this.errorMessage = 'Failed to load vendor information.';
          this.isLoading = false;
        }
      });
    }
    else {
      this.errorMessage = 'Unable to retrieve user ID.';
      this.isLoading = false;
    }
  }

  // Fetch accepted events and their details
  private fetchAcceptedEvents(vendorId: number): void {
    this.acceptedEvents$ = this.vendorService.getAcceptedEvents(vendorId);
    this.acceptedEvents$?.subscribe({
      next: (eventIds) => {
        console.log('Accepted event IDs:', eventIds); // Debugging accepted event IDs

        if (eventIds.length > 0) {
          // Fetch detailed information for each accepted event ID
          this.eventDetails$ = forkJoin(
            eventIds.map((eventId) =>
              this.eventService.getEventById(eventId)
            )
          );

          this.eventDetails$?.subscribe({
            next: (events) => {
              console.log('Event details fetched:', events); // Debugging event details
              this.cdRef.detectChanges(); // Update the view after all details are fetched
              this.isLoading = false;
            },
            error: (err) => {
              console.error('Error fetching event details:', err);
              this.errorMessage = 'Failed to load event details.';
              this.isLoading = false;
            }
          });
        } else {
          this.errorMessage = 'No accepted events found.';
          this.isLoading = false;
        }
      },
      error: (err) => {
        console.error('Error fetching accepted events:', err);
        this.errorMessage = 'Failed to load accepted events.';
        this.isLoading = false;
      }
    });
  }

  // Mock implementation to retrieve current userId
  private getCurrentUserId(): number | null {
    const userId = sessionStorage.getItem('userId'); // Replace with actual logic
    console.log('Retrieved userId:', userId); // Debugging userId retrieval
    return userId ? parseInt(userId, 10) : null;
  }
}
