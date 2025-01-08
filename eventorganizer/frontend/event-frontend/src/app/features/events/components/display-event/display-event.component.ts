import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { IEvent } from '../../../../core/model/Event';
import { EventService } from '../../../../core/services/event-service/event.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-display-event',
  templateUrl: './display-event.component.html',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  styleUrls: ['./display-event.component.css']
})
export class DisplayEventComponent implements OnInit {
  events: IEvent[] = [];
  userId: number | null = null;
  selectedEvent: IEvent | null = null;
  updating: boolean = false;
  loading = false;
  error = '';

  constructor(
    private eventService: EventService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetchUserId();
    this.fetchEvents();
  }

  fetchUserId(): void {
    const storedUserId = sessionStorage.getItem('userId');
    if (storedUserId) {
      const parsedUserId = parseInt(storedUserId, 10);
      if (!isNaN(parsedUserId)) {
        this.userId = parsedUserId;
      }
    }
  }

  fetchEvents(): void {
    this.loading = true;
    const validUserId = this.userId ?? 0;
    this.eventService.getEventsByUserId(validUserId).subscribe({
      next: (data) => {
        this.events = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error fetching events:', err);
        this.error = 'Failed to load events. Please try again later.';
        this.loading = false;
      },
    });
  }

  // Update Event Methods
  startUpdate(event: IEvent): void {
    this.selectedEvent = { ...event };
    this.updating = true;
  }

  submitUpdate(): void {
    if (!this.selectedEvent) return;

    this.eventService.updateEvent(this.selectedEvent.id!, this.selectedEvent).subscribe({
      next: (updatedEvent) => {
        // Update the event in the local list
        const index = this.events.findIndex((e) => e.id === updatedEvent.id);
        if (index !== -1) this.events[index] = updatedEvent;

        this.updating = false;
        this.selectedEvent = null;
        alert('Event updated successfully!');
      },
      error: (err) => {
        console.error('Error updating event:', err);
        alert('Failed to update event. Please try again.');
      },
    });
  }

  cancelUpdate(): void {
    this.updating = false;
    this.selectedEvent = null;
  }

  // Delete Event
  deleteEvent(eventId: number): void {
    if (confirm('Are you sure you want to delete this event?')) {
      this.eventService.deleteEvent(eventId).subscribe({
        next: () => {
          // Remove the deleted event from the list
          this.events = this.events.filter(event => event.id !== eventId);
          alert('Event deleted successfully!');
        },
        error: (err) => {
          console.error('Error deleting event:', err);
          alert('Failed to delete event. Please try again.');
        },
      });
    }
  }
}
