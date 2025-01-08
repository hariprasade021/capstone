import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Guest, Diet, Rsvp } from '../../model/Guest';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class GuestService {
  private baseUrl = `${environment.apiUrl}/guest`; // Base URL for Guest API

  constructor(private http: HttpClient) {}

  private createAuthorizationHeaders(): HttpHeaders {
    const token = sessionStorage.getItem('jwt');  // Retrieve token from sessionStorage
    console.log(token);
    if (!token) {
      console.error('Authorization token is missing');
    }
    return new HttpHeaders({
      'Authorization': token ? `Bearer ${token}` : ''  // Conditionally include token
    });
  }

  // Create Guest
  createGuest(guest: Guest): Observable<Guest> {
    const headers = this.createAuthorizationHeaders();
    return this.http.post<Guest>(this.baseUrl, guest, { headers });
  }

  // Get Guest by ID
  getGuestById(id: number): Observable<Guest> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<Guest>(`${this.baseUrl}/${id}`, { headers });
  }

  // Get All Guests
  getAllGuests(): Observable<Guest[]> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<Guest[]>(this.baseUrl, { headers });
  }

  // Update Guest
  updateGuest(id: number, guest: Guest): Observable<Guest> {
    const headers = this.createAuthorizationHeaders();
    return this.http.put<Guest>(`${this.baseUrl}/${id}`, guest, { headers });
  }

  // Delete Guest
  deleteGuest(id: number): Observable<void> {
    const headers = this.createAuthorizationHeaders();
    return this.http.delete<void>(`${this.baseUrl}/${id}`, { headers });
  }

  // Get Guests by Event ID
  getGuestsByEventId(eventId: number): Observable<Guest[]> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<Guest[]>(`${this.baseUrl}/event/${eventId}`, { headers });
  }

  // Get Guests by Dietary Preference
  getGuestsByDietaryPreference(dietaryPreference: Diet): Observable<Guest[]> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<Guest[]>(`${this.baseUrl}/diet/${dietaryPreference}`, { headers });
  }

  // Get Guests by RSVP Status
  getGuestsByRsvpStatus(rsvpStatus: Rsvp): Observable<Guest[]> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<Guest[]>(`${this.baseUrl}/rsvp/${rsvpStatus}`, { headers });
  }

  sendInvitation(id: number, eventName: string, eventDate: string, eventLocation: string): Observable<string> {
    const headers = this.createAuthorizationHeaders();
    return this.http.post<string>(`${this.baseUrl}/${id}/send-invitation`, { eventName, eventDate, eventLocation }, { headers });
  }
  // 'http://localhost:8002/api/guest/1/send-invitation?eventName=Birthday&eventDate=11-12-2024&eventLocation=Trivandrum'
}
