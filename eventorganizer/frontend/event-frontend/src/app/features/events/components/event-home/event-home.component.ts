import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventService } from '../../../../core/services/event-service/event.service';
import { Status } from '../../../../core/model/Event';
import { RouterModule, Router } from '@angular/router'; // Import Router
import { BudgetComponent } from '../budget/budget.component';

interface Event {
  id: number;
  name: string;
  title: string;
  location: string;
  description: string;
  status: Status;
}

@Component({
  selector: 'app-event-home',
  templateUrl: './event-home.component.html',
  styleUrls: ['./event-home.component.css'],
  standalone: true,
  imports: [CommonModule, RouterModule]
})
export class EventHomeComponent implements OnInit {
  events: Event[] = [];
  filteredEvents: Event[] = [];
  loading = false;
  error: string | null = null;

  // Available status filters
  statusFilter: Status[] = [
    Status.IN_PROGRESS,
    Status.PENDING,
    Status.COMPLETED,
    Status.CANCELLED
  ];

  // Current active filter (defaults to IN_PROGRESS)
  currentFilter: Status = Status.IN_PROGRESS;

  constructor(
    private eventService: EventService,
    private router: Router // Inject Router
  ) {}

  ngOnInit(): void {
    // Fetch events when component initializes
    this.fetchEvents();
  }

  fetchEvents(): void {
    this.loading = true;
    const userId = sessionStorage.getItem('userId') || '0';

    this.eventService.getEventsByUserId(Number(userId)).subscribe({
      next: (data) => {
        this.events = data.map(event => ({
          id: event.id as number,
          name: event.name,
          title: event.title,
          location: event.location,
          description: event.description,
          status: event.status
        }));

        // By default, filter to IN_PROGRESS events
        this.filterEvents(Status.IN_PROGRESS);
        this.loading = false;
      },
      error: (err) => {
        console.error('Error fetching events:', err);
        this.error = 'Failed to load events. Please try again later.';
        this.loading = false;
      },
    });
  }

  filterEvents(status: Status): void {
    this.currentFilter = status;
    this.filteredEvents = this.events.filter(event => event.status === status);
  }

  goToBudgetPage(eventId: number): void {
    // Store the eventId in session storage
    sessionStorage.setItem('eventId', eventId.toString());

    // Navigate to the budget page
    this.router.navigate(['/budget', eventId]);
  }
}
