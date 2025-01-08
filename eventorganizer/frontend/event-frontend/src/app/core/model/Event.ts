// event.model.ts

export enum EventType {
  CORPORATE = "CORPORATE",
  WEDDING = "WEDDING",
  COMMUNITY_FESTIVAL = "COMMUNITY_FESTIVAL",
  BIRTHDAY = "BIRTHDAY",
  CONCERT = "CONCERT",
  SPORTS = "SPORTS",
  CHARITY = "CHARITY",
}

export enum Status {
  PENDING = 'PENDING',
  IN_PROGRESS = 'IN_PROGRESS',
  COMPLETED = 'COMPLETED',
  CANCELLED = 'CANCELLED',
}

export interface IEvent {
  id?: number;
  userId: number | null;
  name: string;
  title: string;
  location: string;
  description: string;
  estimatedExpense: number | null;
  actualExpense: number | null;
  startDate: string;
  endDate: string;
  type: EventType;
  status: Status;
  // clientId: number | null;
  vendors: number[];  // Change from string[] to number[]
  guests: number[];   // Change from string[] to number[]
}

export interface EventRequest {
  id: number;
  eventId: number;
  vendorId: number;
  status: IStatus;//need to change here
}

export enum IStatus {
  APPROVED = 'APPROVED',
  REJECTED = 'REJECTED',
  PENDING = 'PENDING',
}
