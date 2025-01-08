import { Vendor } from './core/model/Vendor';
import { Routes } from '@angular/router';
import { MainComponent } from './core/components/main/main.component';
import { StartPageComponent } from './core/components/start-page/start-page.component';
import { LoginFormComponent } from './features/auth/components/login-form/login-form.component';
import { SignupFormComponent } from './features/auth/components/signup-form/signup-form.component';
import { EventDashboardComponent } from './features/dashboard/components/event-dashboard/event-dashboard.component';
import { AddEventFormComponent } from './features/events/components/add-event-form/add-event-form.component';
import { DisplayEventComponent } from './features/events/components/display-event/display-event.component';
import { AuthGuard } from './features/auth/auth.guard';
import { Role } from './core/model/User';
import { DisplayVendorComponent } from './features/events/components/view-vendors/view-vendors.component';
import { DisplayGuestComponent } from './features/events/components/view-guests/view-guests.component';
import { AddTaskFormComponent } from './features/events/components/add-task-form/add-task-form.component';
import { DisplayTaskComponent } from './features/events/components/display-task/display-task.component';
import { ViewReportComponent } from './features/events/components/view-report/view-report.component';
import { BudgetComponent } from './features/events/components/budget/budget.component';
import { EventHomeComponent } from './features/events/components/event-home/event-home.component';
import { VendorDashboardComponent } from './features/dashboard/components/vendor-dashboard/vendor-dashboard.component';
import { VendorHomeComponent } from './features/vendor/components/vendor-home/vendor-home.component';
import { AddVendorComponent } from './features/vendor/components/add-vendor/add-vendor.component';
import { EventRequestsComponent } from './features/vendor/components/event-requests/event-requests.component';
import { ViewEventsComponent } from './features/vendor/components/view-events/view-events.component';
import { ProfileComponent } from './features/events/components/profile/profile.component';
import { AddGuestComponent } from './features/admin/components/add-guest/add-guest.component';
import { AdminDashboardComponent } from './features/dashboard/components/admin-dashboard/admin-dashboard.component';
import { VendorProfileComponent } from './features/vendor/components/vendor-profile/vendor-profile/vendor-profile.component';
export const routes: Routes = [
  {
    path: '',
    component: StartPageComponent,
    children: [
      { path: '', component: MainComponent },
      { path: 'login', component: LoginFormComponent },
      { path: 'signup', component: SignupFormComponent },
    ],
  },
  {
    path: 'eventDashboard',
    component: EventDashboardComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.ADMIN, Role.USER] },
    children: [
      {
        path: '',
        component: EventHomeComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ADMIN,Role.USER] },
      },
      {
        path: 'addEvent',
        component: AddEventFormComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ADMIN,Role.USER] },
      },
      {
        path: 'viewAllEvent',
        component: DisplayEventComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ADMIN, Role.USER] },
      },
      {
        path: 'viewEvent',
        component: DisplayEventComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ADMIN, Role.USER] },
      },
      {
        path: 'viewAllVendors',
        component: DisplayVendorComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ADMIN, Role.USER] },
      },
      {
        path: 'viewAllGuests',
        component: DisplayGuestComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ADMIN, Role.USER] },
      },
      {
        path: 'addTask/:eventId',
        component: AddTaskFormComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ADMIN, Role.USER] },
      },
      {
        path: 'budget/:id',
        component: BudgetComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ADMIN, Role.USER] },
      },
      {
        path: 'view-report/:eventId',
        component: ViewReportComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ADMIN, Role.USER] },
      },
      {
        path: 'profile',
        component: ProfileComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ADMIN, Role.USER] },
      },
    ]
  },
  {
    path: 'vendorDashboard',
    component: VendorDashboardComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.VENDOR] },
    children: [
      {
        path:'',
        component:ViewEventsComponent,
        canActivate:[AuthGuard],
        data:{roles:[Role.VENDOR]},
      },
      {
        path:'addVendor',
        component:AddVendorComponent,
        canActivate:[AuthGuard],
        data:{roles:[Role.VENDOR]},
      },
      {
        path:'viewEventRequests',
        component:EventRequestsComponent,
        canActivate:[AuthGuard],
        data:{roles:[Role.VENDOR]},
      },
      {
        path:'viewEvents',
        component:ViewEventsComponent,
        canActivate:[AuthGuard],
        data:{roles:[Role.VENDOR]},
      },
      {
        path:'vendor-profile',
        component:VendorProfileComponent,
        canActivate:[AuthGuard],
        data:{roles:[Role.VENDOR]},
      }

    ]
  },
  {
    path: 'adminDashBoard',
    component: AdminDashboardComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.ADMIN, Role.USER] },
    children: [
      {
        path: '',
        component: EventHomeComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ADMIN,Role.USER] },
      },
      {
        path: 'addEvent',
        component: AddEventFormComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ADMIN,Role.USER] },
      },
      {
        path: 'addGuest',
        component: AddGuestComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ADMIN] },
      },
      {
        path: 'viewAllEvent',
        component: DisplayEventComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ADMIN, Role.USER] },
      },
      {
        path: 'viewEvent',
        component: DisplayEventComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ADMIN, Role.USER] },
      },
      {
        path: 'viewAllVendors',
        component: DisplayVendorComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ADMIN, Role.USER] },
      },
      {
        path: 'viewAllGuests',
        component: DisplayGuestComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ADMIN, Role.USER] },
      },
      {
        path: 'addTask/:eventId',
        component: AddTaskFormComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ADMIN, Role.USER] },
      },
      {
        path: 'budget/:id',
        component: BudgetComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ADMIN, Role.USER] },
      },
      {
        path: 'view-report/:eventId',
        component: ViewReportComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ADMIN, Role.USER] },
      },
      {
        path: 'profile',
        component: ProfileComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.ADMIN, Role.USER] },
      },
    ]
  },

  // Add a separate route for view report outside of eventDashboard nested routes

  // Catch-all route
  { path: '**', redirectTo: 'eventDashboard', pathMatch: 'full' },
];
