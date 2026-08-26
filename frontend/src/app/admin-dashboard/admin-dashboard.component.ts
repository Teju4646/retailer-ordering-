import { Component, OnInit } from '@angular/core';
import { MetricsService } from '../services/metrics.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html'
})
export class AdminDashboardComponent implements OnInit {
  metrics: any = null;

  constructor(private metricsService: MetricsService) {}

  ngOnInit() {
    this.metricsService.getSummary().subscribe(m => this.metrics = m);
  }
}
