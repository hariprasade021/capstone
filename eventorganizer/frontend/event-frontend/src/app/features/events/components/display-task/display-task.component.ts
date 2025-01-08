import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Task } from '../../../../core/model/Task'; // Ensure this path is correct
import { TaskService } from '../../../../core/services/task-service/task.service';

@Component({
  selector: 'app-display-task',
  imports: [CommonModule,FormsModule],
  templateUrl: './display-task.component.html',
  styleUrls: ['./display-task.component.css']
})
export class DisplayTaskComponent implements OnInit {
  tasks: Task[] = [];
  loading = false;
  error: string | null = null;
  eventId!: number; // Event ID will be dynamically retrieved from user input

  constructor(
    private taskService: TaskService,private router:Router // Task service for fetching tasks
  ) {}

  ngOnInit(): void {
    // Initialize tasks or any logic on component load (optional)
  }

  onEventIdSubmit(): void {
    if (this.eventId) {
      this.fetchTasks(this.eventId); // Fetch tasks based on user input
    } else {
      this.error = "Please provide a valid Event ID.";
    }
  }

  fetchTasks(eventId: number): void {
    this.loading = true;
    this.taskService.getTasksByEventId(eventId).subscribe({
      next: (data) => {
        this.tasks = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error fetching tasks:', err);
        this.error = 'Failed to load tasks. Please try again later.';
        this.loading = false;
      },
    });
  }

  viewTaskDetails(taskId: number): void {
    // Navigate to a task details page
    // Assumes a route like `/task/:id` is defined
    // this.router.navigate(['/task', taskId]);
    this.router.navigate(['/task', taskId]);
  }
}


