import { EventDTO } from './event-dto.model';
import { GuestDTO } from './guest-dto.model';
import { VendorDTO } from './vendor-dto.model';
import { TaskDTO } from './task-dto.model';

export interface EventReportDTO {
  eventDetails: EventDTO;
  guestDetails: GuestDTO[];
  vendorDetails: VendorDTO[];
  taskDetails: TaskDTO[];
}
