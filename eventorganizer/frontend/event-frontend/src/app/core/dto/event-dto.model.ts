export interface EventDTO {
  id: number;
  name: string;
  title: string;
  location: string;
  description: string;
  estimatedExpense: number;
  actualExpense: number;
  startDate: string; // ISO format
  endDate: string;   // ISO format
  type: EventType;
  status: Status;
}

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
