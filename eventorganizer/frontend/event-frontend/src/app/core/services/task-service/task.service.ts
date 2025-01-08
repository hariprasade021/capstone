import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Task } from '../../model/Task';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  private baseUrl = `${environment.apiUrl}/task`; // Use base URL from environment

  constructor(private http: HttpClient) {}

  // Create Authorization Headers
  private createAuthorizationHeaders(): HttpHeaders {
    const token = sessionStorage.getItem('jwt'); // Retrieve token from session storage
    if (!token) {
      console.error('Authorization token is missing');
    }
    return new HttpHeaders({
      Authorization: token ? `Bearer ${token}` : '', // Include token conditionally
    });
  }

  // Create Task
  createTask(task: Task): Observable<Task> {
    const headers = this.createAuthorizationHeaders();
    console.log("headers:",headers)
    return this.http.post<Task>(this.baseUrl, task, { headers });
  }

  // Get All Tasks
  getAllTasks(): Observable<Task[]> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<Task[]>(this.baseUrl, { headers });
  }

  // Get Task by ID
  getTaskById(taskId: number): Observable<Task> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<Task>(`${this.baseUrl}/${taskId}`, { headers });
  }

  // Get Tasks by Event ID
  getTasksByEventId(eventId: number): Observable<Task[]> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<Task[]>(`${this.baseUrl}/event/${eventId}`, { headers });
  }

  // Update Task
  updateTask(taskId: number, task: Task): Observable<Task> {
    const headers = this.createAuthorizationHeaders();
    return this.http.put<Task>(`${this.baseUrl}/${taskId}`, task, { headers });
  }

  // Delete Task
  deleteTask(taskId: number): Observable<void> {
    const headers = this.createAuthorizationHeaders();
    return this.http.delete<void>(`${this.baseUrl}/${taskId}`, { headers });
  }

  // Search Tasks by Name
  searchTasksByName(name: string): Observable<Task[]> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<Task[]>(`${this.baseUrl}/search/name`, {
      headers,
      params: { name },
    });
  }

  // Search Tasks by Status
  searchTasksByStatus(status: string): Observable<Task[]> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<Task[]>(`${this.baseUrl}/search/status`, {
      headers,
      params: { status },
    });
  }

  // Search Tasks by Deadline Range
  searchTasksByDeadline(startDate: string, endDate: string): Observable<Task[]> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<Task[]>(`${this.baseUrl}/search/deadlines`, {
      headers,
      params: { startDate, endDate },
    });
  }
}
