import { UserService } from './../../../../core/services/user-service/user.service';
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../auth.service';

@Component({
  selector: 'app-login-form',
  imports: [ReactiveFormsModule, CommonModule,RouterModule],
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css'] // Updated to "styleUrls" (plural)
})
export class LoginFormComponent {
  loginFormGroup!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private userService:UserService
  ) {
    this.loginFormGroup = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      role: ['', Validators.required] // Added role with validation
    });
  }

  submitLoginForm() {
    const loginRequest = {
      username: this.loginFormGroup.get('username')!.value,
      password: this.loginFormGroup.get('password')!.value,
      role: this.loginFormGroup.get('role')!.value
    };

    this.authService.login(loginRequest).subscribe({
      next: (token) => {
        this.authService.saveToken(token.jwtToken);

        this.userService.getUserId().subscribe({
          next: (userId) => {
            if (userId !== null) {
              sessionStorage.setItem('userId', userId.toString());

              if (loginRequest.role === 'ADMIN') {
                this.router.navigate(['/adminDashBoard']);
              } else if (loginRequest.role === 'USER') {
                this.router.navigate(['/eventDashboard']);
              }else if (loginRequest.role === 'VENDOR') {
                  this.router.navigate(['/vendorDashboard']);
              } else {
                console.error('Unknown role');
                alert('Login successful, but role is unknown.');
              }
            } else {
              console.error('Could not retrieve user ID');
              alert('Login successful, but unable to retrieve user ID');
            }
          },
          error: (error) => {
            console.error('Failed to retrieve user ID:', error);
            alert('Login successful, but failed to retrieve user ID');
          }
        });
      },
      error: (error) => {
        console.error('Login failed:', error);
        alert('Login failed. Please check your credentials.');
      }
    });
  }



}
