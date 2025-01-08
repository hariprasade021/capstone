import { Component } from '@angular/core';
import { DashboardHeaderComponent } from '../../../../core/components/dashboard-header/dashboard-header.component';
import { DashboardFooterComponent } from '../../../../core/components/dashboard-footer/dashboard-footer.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-admin-dashboard',
  imports: [DashboardHeaderComponent,DashboardFooterComponent,RouterModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent {

}
