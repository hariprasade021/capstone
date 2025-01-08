export interface TaskDTO {
  id: number;
  eventId: number;
  name: string;
  description: string;
  status: TaskStatus;
  deadline: string; // ISO format
}

export enum TaskStatus {
  PENDING = "PENDING",
  IN_PROGRESS = "IN_PROGRESS",
  COMPLETED = "COMPLETED"
}
