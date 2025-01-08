export interface VendorDTO {
  vendorId: number;
  vendorCompanyName: string;
  vendorServiceType: string;
  vendorAmount: number;
  vendorPaymentStatus: PaymentStatus;
}

export enum PaymentStatus {
  PENDING = "PENDING",
  COMPLETED = "COMPLETED"
}
