import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, of } from 'rxjs';
import { User } from '../../model/User';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl = `${environment.apiUrl}/users`; // Base URL for User API

  constructor(private http: HttpClient) {}



  private createAuthorizationHeaders(): HttpHeaders {
    const token = sessionStorage.getItem('jwt'); // Retrieve token from sessionStorage
    if (!token) {
      console.error('Authorization token is missing');
    }
    return new HttpHeaders({
      Authorization: token ? `Bearer ${token}` : '', // Conditionally include token
    });
  }

  getUserId(): Observable<number | null> {
    const username = sessionStorage.getItem('username');

    if (!username) {
      console.warn('Username not found in session storage.');
      return of(null);
    }

    // Add headers
    const headers = this.createAuthorizationHeaders();

    // Corrected URL to match the backend endpoint
    return this.http.get<number>(`http://localhost:8888/api/users/getUserIdByUsername/{username}?username=${username}`, {
      headers,
    }).pipe(
      catchError((error) => {
        console.error('Error fetching user ID', error);
        return of(null);
      })
    );
}



  // Create User
  createUser(user: User): Observable<User> {
    const headers = this.createAuthorizationHeaders();
    return this.http.post<User>(this.baseUrl, user, { headers });
  }

  // Get User by ID
  getUserById(id: number): Observable<User> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<User>(`${this.baseUrl}/${id}`, { headers });
  }
}
