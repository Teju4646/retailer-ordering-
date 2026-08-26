import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RetailerCatalogComponent } from './retailer-catalog/retailer-catalog.component';
import { FranchiseQueueComponent } from './franchise-queue/franchise-queue.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';

const routes: Routes = [
  { path: '', redirectTo: '/retailer', pathMatch: 'full' },
  { path: 'retailer', component: RetailerCatalogComponent },
  { path: 'franchise', component: FranchiseQueueComponent },
  { path: 'admin', component: AdminDashboardComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
