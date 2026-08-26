import { Component, OnInit } from '@angular/core';
import { CatalogService, Product } from '../services/catalog.service';
import { DirectoryService, Retailer } from '../services/directory.service';
import { OrderService } from '../services/order.service';

@Component({
  selector: 'app-retailer-catalog',
  templateUrl: './retailer-catalog.component.html'
})
export class RetailerCatalogComponent implements OnInit {
  retailers: Retailer[] = [];
  selectedRetailerId = '';
  products: Product[] = [];
  cart: { product: Product; qty: number }[] = [];
  placedOrder: any = null;

  constructor(
    private catalogService: CatalogService,
    private directoryService: DirectoryService,
    private orderService: OrderService
  ) {}

  ngOnInit() {
    this.directoryService.getRetailers().subscribe(r => this.retailers = r);
    this.catalogService.getProducts().subscribe(p => this.products = p);
  }

  addToCart(product: Product) {
    const existing = this.cart.find(c => c.product.sku === product.sku);
    if (existing) { existing.qty++; }
    else { this.cart.push({ product, qty: 1 }); }
  }

  get subtotal() {
    return this.cart.reduce((sum, c) => sum + c.product.unitPrice * c.qty, 0);
  }

  placeOrder() {
    if (!this.selectedRetailerId || this.cart.length === 0) return;

    const order = {
      retailerId: this.selectedRetailerId,
      lineItems: this.cart.map(c => ({
        productSku: c.product.sku,
        quantity: c.qty
      }))
    };

    this.orderService.placeOrder(order).subscribe((saved: any) => {
      this.placedOrder = saved;
      this.cart = [];
    });
  }
}
