export type RequestSupplierDto = {
  name: string;
  cnpj: string;
  contactName: string;
  phoneNumber: string;
  email: string;
  address: string;
  city: string;
  state: string;
}

export type ResponseSupplierDto = {
  id: number;
  name: string;
  cnpj: string;
  contactName: string;
  phoneNumber: string;
  email: string;
  address: string;
  city: string;
  state: string;
  active: boolean;
  createdAt: Date;
}

export type SupplierPage = {
  content: ResponseSupplierDto[];

  page: {
    size: number;
    number: number;
    totalElements: number;
    totalPages: number;
  }
}
