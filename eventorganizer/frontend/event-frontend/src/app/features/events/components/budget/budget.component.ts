import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Chart } from 'chart.js/auto';
import { IEvent } from '../../../../core/model/Event';
import { EventService } from '../../../../core/services/event-service/event.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-budget',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './budget.component.html',
  styleUrls: ['./budget.component.css']
})
export class BudgetComponent implements OnInit {
  eventId: number | null = null;
  formSubmitted = false;
  submittedEvent: IEvent | null = null;
  chart: Chart | null = null;

  constructor(
    private eventService: EventService,
    private cdRef: ChangeDetectorRef,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    // Try to get eventId from route parameter
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.eventId = +id; // Convert to number
        this.fetchEventDetails();
      } else {
        // Fallback to session storage
        const storedEventId = sessionStorage.getItem('eventId');
        if (storedEventId) {
          this.eventId = +storedEventId;
          this.fetchEventDetails();
        }
      }
    });
  }

  private fetchEventDetails(): void {
    if (this.eventId !== null) {
      this.eventService.getEventById(this.eventId).subscribe({
        next: (eventData) => {
          this.submittedEvent = eventData;
          this.formSubmitted = true;

          this.cdRef.detectChanges();
          setTimeout(() => this.renderExpenseChart(eventData), 0);
        },
        error: (error) => {
          console.error('Error fetching event:', error);
          alert('Event not found or error occurred.');
        },
      });
    }
  }

  private renderExpenseChart(event: IEvent): void {
    // Ensure the DOM is updated before trying to get the canvas
    setTimeout(() => {
      const canvas = document.getElementById('expenseChart') as HTMLCanvasElement;

      if (!canvas) {
        console.error('Canvas element not found');
        return;
      }

      // Destroy existing chart if any
      if (this.chart) {
        this.chart.destroy();
      }

      // Create the new chart
      this.chart = new Chart(canvas, {
        type: 'bar',
        data: {
          labels: ['Estimated Expense', 'Actual Expense'],
          datasets: [
            {
              label: 'Expense Comparison',
              data: [event.estimatedExpense || 0, event.actualExpense || 0],
              backgroundColor: ['rgba(54, 162, 235, 0.6)', 'rgba(255, 99, 132, 0.6)'],
            },
          ],
        },
        options: {
          responsive: true,
          plugins: {
            legend: {
              display: false,
            },
            title: {
              display: true,
              text: `Expense Comparison for ${event.title}`,
            },
          },
          scales: {
            x: {
              title: {
                display: true,
                text: 'Expense Type',
              },
            },
            y: {
              title: {
                display: true,
                text: 'Amount',
              },
              beginAtZero: true,
            },
          },
        },
      });
    }, 0); // Use timeout to ensure the DOM is updated
  }
}
