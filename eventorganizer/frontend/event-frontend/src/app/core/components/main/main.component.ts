import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { RouterLink, Router } from '@angular/router';
import { ViewportScroller } from '@angular/common';

@Component({
  selector: 'app-main',
  standalone: true, // If this is a standalone component
  imports: [ RouterLink],
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css'], // Corrected from styleUrl to styleUrls
})
export class MainComponent {
  constructor(private viewportScroller: ViewportScroller, private router: Router) {}

  ngOnInit(): void {
    // Scroll to the top of the page when the component initializes
    this.viewportScroller.scrollToPosition([0, 0]);
  }
  scrollToTop() {
    this.viewportScroller.scrollToPosition([0, 0]);
  }

}
