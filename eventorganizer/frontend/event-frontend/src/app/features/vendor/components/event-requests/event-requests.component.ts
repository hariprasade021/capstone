import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VendorService } from '../../../../core/services/vendor-service/vendor.service';
import { EventService } from '../../../../core/services/event-service/event.service';
import { EventRequest, IEvent } from '../../../../core/model/Event';

@Component({
  selector: 'app-event-requests',
  imports: [CommonModule],
  templateUrl: './event-requests.component.html',
  styleUrls: ['./event-requests.component.css'],
})
export class EventRequestsComponent implements OnInit {
  @Output() acceptedEventsChange = new EventEmitter<IEvent[]>(); // Emit accepted events to parent component

  eventRequests: EventRequest[] = [];
  acceptedEvents: IEvent[] = [];
  eventDetails: { [key: number]: IEvent } = {};
  id: number | null = null; // Dynamically set vendorId
  isLoading: boolean = false; // Tracks loading state
  errorMessage: string | null = null; // Displays errors if any

  constructor(
    private vendorService: VendorService,
    private eventService: EventService
  ) {}

  ngOnInit(): void {
    this.initializeVendorId();
  }

  // Initialize vendorId based on userId
  private initializeVendorId(): void {
    const userId = this.getCurrentUserId(); // Fetch userId from a method or service
    if (userId) {
      this.fetchVendorIdByUserId(userId);
    } else {
      this.errorMessage = 'Unable to fetch user ID.';
    }
  }

  // Fetch vendorId using userId
  private fetchVendorIdByUserId(userId: number): void {
    this.vendorService.getVendorByUserId(userId).subscribe({
      next: (vendor) => {
        this.id = vendor.vendorId ?? null; // Assign null if vendorId is undefined
        this.loadEventRequests();
      },
      error: (err) => {
        console.error('Error fetching vendor ID:', err);
        this.errorMessage = 'Failed to fetch vendor information.';
      },
    });
  }


  // Mock implementation to retrieve current userId
  private getCurrentUserId(): number | null {
    const userId = sessionStorage.getItem('userId');
    return userId ? parseInt(userId, 10) : null; // Replace with actual logic to retrieve the user ID
  }

  // Load all pending event requests for the vendor
  private loadEventRequests(): void {
    if (!this.id) return;

    this.isLoading = true;
    this.errorMessage = null;

    this.vendorService.getPendingRequests(this.id).subscribe({
      next: (requests: EventRequest[]) => {
        this.eventRequests = requests;
        this.fetchEventDetailsForRequests();
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error fetching event requests:', err);
        this.errorMessage = 'Unable to load event requests.';
        this.isLoading = false;
      },
    });
  }

  // Fetch event details for all pending requests
  private fetchEventDetailsForRequests(): void {
    this.eventRequests.forEach((request) => {
      if (!this.eventDetails[request.eventId]) {
        this.eventService.getEventById(request.eventId).subscribe({
          next: (event: IEvent) => {
            this.eventDetails[request.eventId] = event;
          },
          error: (err) => {
            console.error(`Error fetching event ID ${request.eventId}:`, err);
          },
        });
      }
    });
  }

  // Handle response to an event request (ACCEPT/DECLINE)
  private handleRequestResponse(requestId: number, response: string): void {
    if (!this.id) return;

    this.isLoading = true;
    this.vendorService.respondToRequest(this.id, requestId, response).subscribe({
      next: () => {
        alert(`Request ${requestId} has been ${response.toLowerCase()}.`);
        this.loadEventRequests();

        if (response === 'ACCEPT') {
          this.addAcceptedEvent(requestId);
        }
      },
      error: (err) => {
        console.error(`Error processing request ID ${requestId}:`, err);
        this.errorMessage = 'Failed to process the request.';
        this.isLoading = false;
      },
    });
  }

  // Add an accepted event to the list of accepted events
  private addAcceptedEvent(requestId: number): void {
    const request = this.eventRequests.find((r) => r.id === requestId);
    if (request) {
      this.eventService.getEventById(request.eventId).subscribe({
        next: (event: IEvent) => {
          if (!this.acceptedEvents.find((e) => e.id === event.id)) {
            this.acceptedEvents.push(event);
            this.acceptedEventsChange.emit(this.acceptedEvents); // Emit updated list
          }
        },
        error: (err) => {
          console.error(`Error fetching details for accepted event ID ${request.eventId}:`, err);
        },
      });
    }
  }

  // Public method to accept an event request
  acceptRequest(requestId: number): void {
    this.handleRequestResponse(requestId, 'ACCEPT');
  }

  // Public method to reject an event request
  rejectRequest(requestId: number): void {
    this.handleRequestResponse(requestId, 'DECLINE');
  }
}
