import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { User } from '../../../../core/model/User';
import { AuthService } from '../../auth.service';
import { debounceTime, switchMap, catchError } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-signup-form',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './signup-form.component.html',
  styleUrls: ['./signup-form.component.css'],
})
export class SignupFormComponent {
  signupForm: FormGroup;
  usernameExists: boolean = false;
  passwordMatchError: boolean = false;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private fb: FormBuilder, private authService: AuthService) {
    this.signupForm = this.fb.group({
      username: [
        '',
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(20),
          Validators.pattern('^[a-zA-Z0-9]+$'),
        ],
      ],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/),
        ],
      ],
      confirmPassword: ['', Validators.required],
      role: ['', Validators.required],
    });

    // Check username availability when the username field is updated
    this.signupForm.get('username')?.valueChanges.pipe(
      debounceTime(500),
      switchMap((username) => this.authService.checkUsernameExists(username)),
      catchError(() => of(false))
    ).subscribe((exists) => {
      this.usernameExists = exists;
    });
  }

  onSubmit() {
    if (this.signupForm.invalid) {
      return;
    }

    const formValue = this.signupForm.value;
    this.passwordMatchError = formValue.password !== formValue.confirmPassword;

    if (!this.passwordMatchError && !this.usernameExists) {
      const user: User = {
        username: formValue.username,
        password: formValue.password,
        role: formValue.role,
      };

      this.authService.signup(user).subscribe({
        next: (response) => {
          this.successMessage = 'User registered successfully';
          this.errorMessage = '';
          console.log('User registered successfully:', response);
        },
        error: (error) => {
          this.errorMessage = error.error;
          this.successMessage = '';
          console.error('Registration failed:', error);
        },
      });
    }
  }
}
