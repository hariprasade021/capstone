import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Vendor } from '../../model/Vendor';
import { environment } from '../../../../environments/environment';
import { EventRequest, IEvent } from '../../model/Event';

@Injectable({
  providedIn: 'root',
})
export class VendorService {
  private baseUrl = `${environment.apiUrl}/vendors`; // Base URL for Vendor API

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

  // Create Vendor
  createVendor(vendor: Vendor): Observable<Vendor> {
    const headers = this.createAuthorizationHeaders();
    return this.http.post<Vendor>(this.baseUrl, vendor, { headers });
  }

  // Get Vendor by ID
  getVendorById(vendorId: number): Observable<Vendor> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<Vendor>(`${this.baseUrl}/${vendorId}`, { headers });
  }

  // Get All Vendors
  getAllVendors(): Observable<Vendor[]> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<Vendor[]>(this.baseUrl, { headers });
  }

  // Update Vendor
  updateVendor(vendorId: number, vendor: Vendor): Observable<Vendor> {
    const headers = this.createAuthorizationHeaders();
    return this.http.put<Vendor>(`${this.baseUrl}/${vendorId}`, vendor, { headers });
  }

  // Delete Vendor
  deleteVendor(vendorId: number): Observable<void> {
    const headers = this.createAuthorizationHeaders();
    return this.http.delete<void>(`${this.baseUrl}/${vendorId}`, { headers });
  }

  // Get Vendors by Service Type
  getVendorsByServiceType(serviceType: string): Observable<Vendor[]> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<Vendor[]>(`${this.baseUrl}/service/${serviceType}`, { headers });
  }

  // Get Vendors by Payment Status
  // getVendorsByPaymentStatus(paymentStatus: PaymentStatus): Observable<Vendor[]> {
  //   const headers = this.createAuthorizationHeaders();
  //   return this.http.get<Vendor[]>(`${this.baseUrl}/paymentStatus/${paymentStatus}`, { headers });
  // }
  getPendingRequests(vendorId: number): Observable<EventRequest[]> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<EventRequest[]>(`${this.baseUrl}/${vendorId}/requests`, { headers });
  }


  respondToRequest(vendorId: number, requestId: number, response: string): Observable<string> {
    const headers = this.createAuthorizationHeaders();
    return this.http.post(`${this.baseUrl}/${vendorId}/requests/${requestId}?response=${response}`, null, {
      headers,
      responseType: 'text', // Explicitly specify response type as 'text'
    }) as Observable<string>; // Cast the result to Observable<string>
  }

  addAcceptedEvent(vendorId: number, event: IEvent): Observable<Vendor> {
    const headers = this.createAuthorizationHeaders();
    return this.http.post<Vendor>(`${this.baseUrl}/${vendorId}/accepted-events`, event,{headers});
  }

  // Method to get all accepted events for a vendor
  getAcceptedEvents(vendorId: number): Observable<number[]> {
    const headers = this.createAuthorizationHeaders();
    return this.http.get<number[]>(`${this.baseUrl}/${vendorId}/accepted-events`, { headers });
}

getVendorByUserId(userId: number): Observable<Vendor> {
  const headers = this.createAuthorizationHeaders();
  return this.http.get<Vendor>(`${this.baseUrl}/byUser/${userId}`, { headers });
}

getVendorIdByUserId(userId: number): Observable<number> {
  const headers = this.createAuthorizationHeaders();
  return this.http.get<number>(`${this.baseUrl}/vendorIdByUseId/${userId}`, { headers });
}


}
