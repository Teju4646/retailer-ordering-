import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class OrderService {
  constructor(private http: HttpClient) {}

  placeOrder(order: any) {
    return this.http.post(`${environment.apiUrl}/orders`, order);
  }

  updateStatus(orderId: string, status: string) {
    return this.http.patch(`${environment.apiUrl}/orders/${orderId}/status?status=${status}`, {});
  }

  getByRetailer(retailerId: string) {
    return this.http.get<any[]>(`${environment.apiUrl}/orders/retailer/${retailerId}`);
  }

  getByFranchise(franchiseId: string) {
    return this.http.get<any[]>(`${environment.apiUrl}/orders/franchise/${franchiseId}`);
  }
}
