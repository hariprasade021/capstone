import { Component, OnInit } from '@angular/core';
import { TaskService } from '../../../../core/services/task-service/task.service';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Status, Task } from '../../../../core/model/Task';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-task-form',
  imports: [FormsModule, CommonModule],
  templateUrl: './add-task-form.component.html',
  styleUrls: ['./add-task-form.component.css'],
  standalone: true
})
export class AddTaskFormComponent implements OnInit {
  taskData: Task = {
    eventId: 0,
    name: '',
    description: '',
    status: Status.PENDING,
    deadline: '',
  };

  constructor(
    private taskService: TaskService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    // Retrieve event ID from route parameters
    this.route.params.subscribe(params => {
      const eventId = +params['eventId']; // Convert to number
      if (eventId) {
        this.taskData.eventId = eventId;
      }
    });
  }

  onSubmit(taskForm: NgForm): void {
    if (taskForm.valid) {
      const taskPayload: Task = {
        ...this.taskData,
      };

      this.taskService.createTask(taskPayload).subscribe(
        (response) => {
          console.log('Task created successfully:', response);
          alert("Task Added Successfully");
          // Navigate back to event home or tasks list
          this.router.navigate(['/eventDashboard']);
        },
        (error) => {
          console.error('Error occurred while creating task:', error);
          alert("Failed to add task");
        }
      );
    }
  }
}
