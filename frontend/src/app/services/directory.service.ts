import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

export interface Retailer {
  id: string; name: string; city: string; state: string; gstin: string;
}
export interface Franchise {
  id: string; name: string; region: string; state: string;
}

@Injectable({ providedIn: 'root' })
export class DirectoryService {
  constructor(private http: HttpClient) {}

  getRetailers() {
    return this.http.get<Retailer[]>(`${environment.apiUrl}/directory/retailers`);
  }

  getFranchises() {
    return this.http.get<Franchise[]>(`${environment.apiUrl}/directory/franchises`);
  }
}
