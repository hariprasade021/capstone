import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable, switchMap, tap } from 'rxjs';
import { Role, User } from '../../core/model/User';
import { log } from 'console';
import { UserService } from '../../core/services/user-service/user.service';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly baseUrl = "http://localhost:8888/api";

  constructor(private http: HttpClient, private userService: UserService) {}


  login(loginRequest: { username: string; password: string; role: Role }): Observable<{ jwtToken: string }> {
    return this.http.post<{ jwtToken: string }>(
      "http://localhost:8888/api/login",
      loginRequest
    ).pipe(
      tap((response) => {
        // Save the username to session storage
        sessionStorage.setItem('username', loginRequest.username);
      })
    );
  }

  saveToken(token: string): void {
    sessionStorage.setItem('jwt', token);
  }

  getToken(): string | null {
    return sessionStorage.getItem('jwt');
  }

  getRole(): string | null {
    const token = this.getToken();

    if (token) {
      try {
        // Decode the JWT token to extract the payload
        const payload = JSON.parse(atob(token.split('.')[1])); // Decodes the payload part of the JWT

        return payload.role || null;  // Return the role, or null if it's not available
      } catch (error) {
        console.error('Error decoding token:', error);
        return null;
      }
    }
    return null;
  }

  // getUserId(){
  //   const username = sessionStorage.getItem('username');

  // }


  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  logout(): void {
    sessionStorage.removeItem('jwt'); // Remove the specific 'jwt' item from sessionStorage
    sessionStorage.clear(); // Clear all items from sessionStorage
  }


  checkUsernameExists(username: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.baseUrl}/check-username/${username}`);
  }

  signup(user: User): Observable<string>
  {

    return this.http.post<string>(`${this.baseUrl}/register`, user);
  }


}
