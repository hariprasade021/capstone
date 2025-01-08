import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IEvent } from '../../model/Event';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private baseUrl = `${environment.apiUrl}/events`;  // Use base URL from environment

  constructor(private http: HttpClient) { }

  private userId!: number;

  setUserId(id: number): void {

    this.userId = id;
  }

  getUserId(): number {
    return this.userId;
  }

  private createAuthorizationHeaders(): HttpHeaders {
    const token = sessionStorage.getItem('jwt');  // Retrieve token from localStorage
    console.log(token);
    if (!token) {
      console.error('Authorization token is missing');
    }
    return new HttpHeaders({
      'Authorization': token ? `Bearer ${token}` : ''  // Conditionally include token
    });
  }


  // Create Event
  createEvent(event: IEvent): Observable<IEvent> {
    const token = sessionStorage.getItem('jwt');
    return this.http.post<IEvent>(this.baseUrl, event, {
      headers: {
        "Authorization": "Bearer " + token
      }
    });
  }


  // Get All Events
  getAllEvents(): Observable<IEvent[]> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<IEvent[]>(this.baseUrl, { headers });
  }

  // Get Event by ID
  getEventById(id: number): Observable<IEvent> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<IEvent>(`${this.baseUrl}/${id}`, { headers });
  }

  // Update Event
  updateEvent(id: number, event: IEvent): Observable<IEvent> {
    const headers = this.createAuthorizationHeaders();
    return this.http.put<IEvent>(`${this.baseUrl}/${id}`, event, { headers });
  }

  // Delete Event
  deleteEvent(id: number): Observable<void> {
    const headers = this.createAuthorizationHeaders();
    return this.http.delete<void>(`${this.baseUrl}/${id}`, { headers });
  }

  // Search Events by Name
  searchEventsByName(name: string): Observable<IEvent[]> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<IEvent[]>(`${this.baseUrl}/search/name`, {
      headers,
      params: { name }
    });
  }

  // Search Events by Date Range
  searchEventsByDateRange(startDate: string, endDate: string): Observable<IEvent[]> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<IEvent[]>(`${this.baseUrl}/search/dates`, {
      headers,
      params: { startDate, endDate }
    });
  }

  // Get All Guests for an Event
  getAllGuestsByEventId(eventId: number): Observable<any[]> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<any[]>(`${this.baseUrl}/getAllGuests/${eventId}`, { headers });
  }

  // Get Event Report
  getEventReport(eventId: number): Observable<any> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<any>(`${this.baseUrl}/getEventReport/${eventId}`, { headers });
  }

  // Get Events by User ID
  getEventsByUserId(userId: number): Observable<IEvent[]> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<IEvent[]>(`${this.baseUrl}/user/${userId}`, { headers });
  }

  // Add this method to the EventService class
  requestVendor(eventId: number, vendorId: number): Observable<string> {
    const headers = this.createAuthorizationHeaders();

    return this.http.post(
      `http://localhost:8888/api/events/${eventId}/request-vendor/${vendorId}`,
      null,
      {
        headers: headers,
        responseType: 'text' // Explicitly set response type to text
      }
    );
  }

}
