import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot } from '@angular/router';
import { AuthService } from './auth.service';
import { Role } from '../../core/model/User';
import { log } from 'console';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const token = this.authService.getToken();
    const userRole = this.authService.getRole(); // This could be string or null

    // Check if userRole is not null and valid
    if (userRole !== null) {
      const allowedRoles: Role[] = route.data?.['roles'] || [];

      // Check if the user's role is included in allowed roles
      if (allowedRoles.includes(userRole as Role)) {
        return true;
      }
    }

    // Unauthorized access
    console.error('Access denied - insufficient permissions or not authenticated');
    this.router.navigate(['/login']);
    return false;
  }
}
