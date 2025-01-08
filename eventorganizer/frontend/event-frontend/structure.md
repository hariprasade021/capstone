src/
├── app/
│   ├── core/                     # Core module for global services and configurations
│   │   ├── components/           # Reusable components like header, footer, navbar
│   │   │   ├── header/
│   │   │   ├── footer/
│   │   │   ├── top-navbar/
│   │   │   ├── notification-dialog/
│   │   │   └── navbar/
│   │   ├── services/
│   │   │   ├── event.service.ts
│   │   │   ├── guest.service.ts
│   │   │   ├── vendor.service.ts
│   │   │   └── notification.service.ts
│   │   └── guards/
│   │       └── auth.guard.ts
│   ├── shared/                   # Shared module for common pipes, directives, utilities
│   │   ├── models/
│   │   │   ├── event.model.ts
│   │   │   ├── guest.model.ts
│   │   │   └── vendor.model.ts
│   │   └── components/
│   │       ├── event-card/
│   │       └── vendor-card/
│   ├── features/                 # Feature modules organized by domain
│   │   ├── auth/                 # Authentication module
│   │   │   ├── components/
│   │   │   │   ├── login-form/
│   │   │   │   └── signup-form/
│   │   │   ├── services/
│   │   │   │   └── auth.service.ts
│   │   │   └── auth.module.ts
│   │   ├── dashboard/            # Dashboard module
│   │   │   ├── components/
│   │   │   │   ├── dashboard/
│   │   │   │   ├── notifications/
│   │   │   │   ├── organizer-dashboard/
│   │   │   │   │   ├── organizer-dashboard.component.ts
│   │   │   │   │   ├── organizer-dashboard.component.html
│   │   │   │   │   └── organizer-dashboard.component.css
│   │   │   │   ├── guest-dashboard/
│   │   │   │   │   ├── guest-dashboard.component.ts
│   │   │   │   │   ├── guest-dashboard.component.html
│   │   │   │   │   └── guest-dashboard.component.css
│   │   │   │   ├── vendor-dashboard/
│   │   │   │   │   ├── vendor-dashboard.component.ts
│   │   │   │   │   ├── vendor-dashboard.component.html
│   │   │   │   │   └── vendor-dashboard.component.css
│   │   │   │   └── client-dashboard/
│   │   │   │       ├── client-dashboard.component.ts
│   │   │   │       ├── client-dashboard.component.html
│   │   │   │       └── client-dashboard.component.css
│   │   │   └── dashboard.module.ts
│   │   ├── events/               # Events module
│   │   │   ├── components/
│   │   │   │   ├── add-event-form/
│   │   │   │   ├── edit-event-form/
│   │   │   │   └── event-list/
│   │   │   ├── pages/
│   │   │   │   ├── events/
│   │   │   │   └── event-details/
│   │   │   └── events.module.ts
│   │   ├── guests/               # Guests module
│   │   │   ├── components/
│   │   │   │   ├── add-guest-form/
│   │   │   │   ├── edit-guest-form/
│   │   │   │   └── guest-list/
│   │   │   ├── pages/
│   │   │   │   ├── guests/
│   │   │   │   └── guest-details/
│   │   │   └── guests.module.ts
│   │   ├── vendors/              # Vendors module
│   │   │   ├── components/
│   │   │   │   ├── add-vendor-form/
│   │   │   │   ├── edit-vendor-form/
│   │   │   │   └── vendor-list/
│   │   │   ├── pages/
│   │   │   │   ├── vendors/
│   │   │   │   └── vendor-details/
│   │   │   └── vendors.module.ts
│   │   ├── tasks/                # Tasks module
│   │   │   ├── components/
│   │   │   │   ├── add-task-form/
│   │   │   │   ├── edit-task-form/
│   │   │   │   └── task-list/
│   │   │   ├── pages/
│   │   │   │   ├── tasks/
│   │   │   │   └── task-details/
│   │   │   └── tasks.module.ts
│   ├── app-routing.module.ts
│   ├── app.component.html
│   ├── app.component.ts
│   └── app.module.ts
└── assets/
    ├── images/
    ├── styles/
    └── icons/
