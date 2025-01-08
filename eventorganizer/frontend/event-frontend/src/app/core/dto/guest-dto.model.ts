export interface GuestDTO {
  id: number;
  name: string;
  diet: Diet;
  rsvpStatus: Rsvp;
}

export enum Diet {
  VEG = 'VEG',
  NON_VEG = 'NON_VEG'
}

export enum Rsvp {
  REGISTERED = 'REGISTERED',
  DECLINED = 'DECLINED',
  NO_RESPONSE = 'NO_RESPONSE',
}
