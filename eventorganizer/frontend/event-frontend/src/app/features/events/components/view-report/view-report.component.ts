import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EventService } from '../../../../core/services/event-service/event.service';
import { CommonModule } from '@angular/common';
import jsPDF from 'jspdf';
import 'jspdf-autotable';
import autoTable from 'jspdf-autotable';

@Component({
  selector: 'app-view-report',
  templateUrl: './view-report.component.html',
  styleUrls: ['./view-report.component.css'],
  standalone: true,
  imports: [CommonModule]
})
export class ViewReportComponent implements OnInit {
  reportData: any |null  = null;
  loading = false;
  error = '';

  constructor(
    private route: ActivatedRoute,
    private eventService: EventService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const eventId = +params['eventId'];
      if (eventId) {
        this.fetchEventReport(eventId);
      }
    });
  }


  fetchEventReport(eventId: number): void {
    this.loading = true;
    this.error = '';

    this.eventService.getEventReport(eventId).subscribe({
      next: (report) => {
        this.reportData = report;
        this.loading = false;
        console.log(this.reportData.guestDetails); // Check actual data structure
      },
      error: (err) => {
        this.error = 'Failed to load event report.';
        console.error(err);
        this.loading = false;
      }
    });
  }

  // downloadPDF(): void {
  //   if (!this.reportData) return;

  //   const doc = new jsPDF();

  //   // Event Details
  //   doc.setFontSize(18);
  //   doc.text('Event Report', 14, 22);

  //   doc.setFontSize(12);
  //   doc.text(`Event Name: ${this.reportData.eventDetails.name}`, 14, 32);
  //   doc.text(`Title: ${this.reportData.eventDetails.title}`, 14, 39);
  //   doc.text(`Location: ${this.reportData.eventDetails.location}`, 14, 46);
  //   doc.text(`Status: ${this.reportData.eventDetails.status}`, 14, 53);

  //   // Guests
  //   doc.autoTable({
  //     head: [['Name', 'Diet', 'RSVP Status']],
  //     body: this.reportData.guestDetails.map((guest: any) => [
  //       guest.name,
  //       guest.diet,
  //       guest.rsvpStatus
  //     ]),
  //     startY: 70,
  //     theme: 'striped',
  //     headStyles: { fillColor: [41, 128, 185] },
  //     margin: { left: 14, right: 14 }
  //   });

  //   // Vendors
  //   doc.autoTable({
  //     head: [['Vendor', 'Service', 'Amount', 'Payment Status']],
  //     body: this.reportData.vendorDetails.map((vendor: any) => [
  //       vendor.vendorCompanyName,
  //       vendor.vendorServiceType,
  //       vendor.vendorAmount,
  //       vendor.vendorPaymentStatus
  //     ]),
  //     theme: 'striped',
  //     headStyles: { fillColor: [41, 128, 185] },
  //     margin: { left: 14, right: 14 }
  //   });

  //   // Tasks
  //   doc.autoTable({
  //     head: [['Task', 'Status', 'Deadline']],
  //     body: this.reportData.taskDetails.map((task: any) => [
  //       task.name,
  //       task.status,
  //       task.deadline
  //     ]),
  //     theme: 'striped',
  //     headStyles: { fillColor: [41, 128, 185] },
  //     margin: { left: 14, right: 14 }
  //   });

  //   doc.save(`Event_Report_${this.reportData.eventDetails.name}.pdf`);
  // }

  // closeReport(): void {
  //   window.history.back();
  // }


  downloadPDF(): void {
    if (!this.reportData) return;

    const doc = new jsPDF();

    // Event Details
    doc.setFontSize(18);
    doc.text('Event Report', 14, 22);

    doc.setFontSize(12);
    doc.text(`Event Name: ${this.reportData.eventDetails.name}`, 14, 32);
    doc.text(`Title: ${this.reportData.eventDetails.title}`, 14, 39);
    doc.text(`Location: ${this.reportData.eventDetails.location}`, 14, 46);
    doc.text(`Status: ${this.reportData.eventDetails.status}`, 14, 53);

    // Guests
    autoTable(doc, {
      head: [['Name', 'Diet', 'RSVP Status']],
      body: this.reportData.guestDetails.map((guest: any) => [
        guest.name,
        guest.diet,
        guest.rsvpStatus
      ]),
      startY: 70,
      theme: 'striped',
      headStyles: { fillColor: [41, 128, 185] },
      margin: { left: 14, right: 14 }
    });

    // Vendors
    autoTable(doc, {
      head: [['Vendor', 'Service', 'Amount', 'Payment Status']],
      body: this.reportData.vendorDetails.map((vendor: any) => [
        vendor.vendorCompanyName,
        vendor.vendorServiceType,
        vendor.vendorAmount,
        vendor.vendorPaymentStatus
      ]),
      theme: 'striped',
      headStyles: { fillColor: [41, 128, 185] },
      margin: { left: 14, right: 14 }
    });

    // Tasks
    autoTable(doc, {
      head: [['Task', 'Status', 'Deadline']],
      body: this.reportData.taskDetails.map((task: any) => [
        task.name,
        task.status,
        task.deadline
      ]),
      theme: 'striped',
      headStyles: { fillColor: [41, 128, 185] },
      margin: { left: 14, right: 14 }
    });

    doc.save(`Event_Report_${this.reportData.eventDetails.name}.pdf`);
  }

  closeReport(): void {
    window.history.back();
  }

  getTaskClass(task: any): string { 
    const currentDate = new Date();
    const taskDeadline = new Date(task.deadline);
    
    // Determine the text color based on status and deadline
    if (task.status === 'COMPLETED') {
      return 'text-success'; // Green text for completed tasks
    } else if (task.status === 'PENDING' && taskDeadline < currentDate) {
      return 'text-danger'; // Red text for pending tasks with past deadline
    } else {
      return ''; // No color for other tasks
    }
  }
  
  
}




