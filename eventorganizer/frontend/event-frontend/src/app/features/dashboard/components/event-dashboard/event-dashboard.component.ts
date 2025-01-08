import { Component } from '@angular/core';
import { DashboardHeaderComponent } from '../../../../core/components/dashboard-header/dashboard-header.component';
import { DashboardFooterComponent } from '../../../../core/components/dashboard-footer/dashboard-footer.component';
import { AddEventFormComponent } from '../../../events/components/add-event-form/add-event-form.component';
import { RouterModule } from '@angular/router';
import { DisplayVendorComponent } from '../../../events/components/view-vendors/view-vendors.component';

@Component({
  selector: 'app-event-dashboard',
  imports: [DashboardHeaderComponent,DashboardFooterComponent,RouterModule],
  templateUrl: './event-dashboard.component.html',
  styleUrl: './event-dashboard.component.css'
})
export class EventDashboardComponent {

}
