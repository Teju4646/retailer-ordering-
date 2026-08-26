import { Component, OnInit } from '@angular/core';
import { DirectoryService, Franchise } from '../services/directory.service';
import { OrderService } from '../services/order.service';

@Component({
  selector: 'app-franchise-queue',
  templateUrl: './franchise-queue.component.html'
})
export class FranchiseQueueComponent implements OnInit {
  franchises: Franchise[] = [];
  selectedFranchiseId = '';
  orders: any[] = [];
  statusOptions = ['CONFIRMED', 'DISPATCHED', 'DELIVERED', 'CANCELLED'];

  constructor(private directoryService: DirectoryService, private orderService: OrderService) {}

  ngOnInit() {
    this.directoryService.getFranchises().subscribe(f => this.franchises = f);
  }

  loadOrders() {
    if (!this.selectedFranchiseId) return;
    this.orderService.getByFranchise(this.selectedFranchiseId).subscribe(o => this.orders = o);
  }

  updateStatus(orderId: string, status: string) {
    this.orderService.updateStatus(orderId, status).subscribe(() => this.loadOrders());
  }
}
