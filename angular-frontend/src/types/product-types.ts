export type RequestProductDto = {
  name: string;
  code: string;
  description: string;
  size: string;
  color: string;
  purchasePrice: number;
  salesPrice: number;
  minimalStock: number;
  stock: number;
  categoryId: number;
  supplierId: number;
};

export type ResponseProductDto = {
  id: number;
  name: string;
  code: string;
  description: string;
  size: string;
  color: string;
  purchasePrice: number;
  salesPrice: number;
  minimalStock: number;
  stock: number;
  category: string;
  supplier: string;
  active: boolean;
  createdAt: string;
  updatedAt: string;
};

export type ProductPage = {
  content: ResponseProductDto[];

  page: {
    size: number;
    number: number;
    totalElements: number;
    totalPages: number;
  }
}
