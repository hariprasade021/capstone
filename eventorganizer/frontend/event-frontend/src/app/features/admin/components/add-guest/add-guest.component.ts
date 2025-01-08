import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { GuestService } from '../../../../core/services/guest-service/guest.service';
import { Guest, Diet, Rsvp } from '../../../../core/model/Guest';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';

@Component({
  selector: 'app-add-guest',
  imports:[CommonModule,FormsModule,ReactiveFormsModule],
  templateUrl: './add-guest.component.html',
  styleUrls: ['./add-guest.component.css'],
})
export class AddGuestComponent {
  guestForm!: FormGroup;
  dietOptions = Object.values(Diet); // Extracting enum values
  rsvpOptions = Object.values(Rsvp); // Extracting enum values
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private fb: FormBuilder, private guestService: GuestService) {
    this.createForm();
  }

  private createForm() {
    this.guestForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      diet: ['', Validators.required],
      rsvpStatus: ['', Validators.required],
    });
  }

  onSubmit() {
    if (this.guestForm.valid) {
      const guest: Guest = this.guestForm.value;
      this.guestService.createGuest(guest).subscribe(
        (newGuest) => {
          this.successMessage = 'Guest created successfully!';
          this.errorMessage = '';
          this.guestForm.reset(); // Reset form after successful submission
        },
        (error) => {
          this.errorMessage = 'Error creating guest. Please try again.';
          this.successMessage = '';
        }
      );
    }
  }
}
