import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DashboardFooterComponent } from '../../../../core/components/dashboard-footer/dashboard-footer.component';
import { DashboardHeaderComponent } from '../../../../core/components/dashboard-header/dashboard-header.component';
import { VendorHeaderComponent } from '../../../../core/components/vendor-header/vendor-header/vendor-header.component';

@Component({
  selector: 'app-vendor-dashboard',
  imports: [RouterModule,VendorHeaderComponent,DashboardFooterComponent],
  templateUrl: './vendor-dashboard.component.html',
  styleUrl: './vendor-dashboard.component.css'
})
export class VendorDashboardComponent {

}
