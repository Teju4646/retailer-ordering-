import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

export interface Product {
  id: string; name: string; sku: string; hsnCode: string;
  unitPrice: number; gstRatePct: number;
}

@Injectable({ providedIn: 'root' })
export class CatalogService {
  constructor(private http: HttpClient) {}

  getProducts() {
    return this.http.get<Product[]>(`${environment.apiUrl}/catalog/products`);
  }
}
