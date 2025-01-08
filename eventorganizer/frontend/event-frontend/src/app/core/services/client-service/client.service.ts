import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Client } from '../../model/Client';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ClientService {
  private baseUrl = `${environment.apiUrl}/client`; // Base URL for Client API

  constructor(private http: HttpClient) {}

  private createAuthorizationHeaders(): HttpHeaders {
    const token = sessionStorage.getItem('jwt'); // Retrieve token from sessionStorage
    console.log(token);
    if (!token) {
      console.error('Authorization token is missing');
    }
    return new HttpHeaders({
      Authorization: token ? `Bearer ${token}` : '', // Conditionally include token
    });
  }

  // Create Client
  createClient(client: Client): Observable<Client> {
    const headers = this.createAuthorizationHeaders();
    return this.http.post<Client>(this.baseUrl, client, { headers });
  }

  // Get Client by ID
  getClientById(clientId: number): Observable<Client> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<Client>(`${this.baseUrl}/${clientId}`, { headers });
  }

  // Get All Clients
  getAllClients(): Observable<Client[]> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<Client[]>(this.baseUrl, { headers });
  }

  // Update Client
  updateClient(clientId: number, client: Client): Observable<Client> {
    const headers = this.createAuthorizationHeaders();
    return this.http.put<Client>(`${this.baseUrl}/${clientId}`, client, { headers });
  }

  // Delete Client
  deleteClient(clientId: number): Observable<void> {
    const headers = this.createAuthorizationHeaders();
    return this.http.delete<void>(`${this.baseUrl}/${clientId}`, { headers });
  }

  // Get Client with Feedback
  getClientWithFeedback(clientId: number): Observable<any> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<any>(`${this.baseUrl}/${clientId}/feedback`, { headers });
  }
}
