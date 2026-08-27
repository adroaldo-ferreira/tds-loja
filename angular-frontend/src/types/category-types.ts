export type RequestCategory = {
  name: string;
  description: string;
}

export type ResponseCategory = {
  id: number;
  name: string;
  description: string;
}

export type CategoryPage = {
  content: ResponseCategory[];

  page: {
    size: number;
    number: number;
    totalElements: number;
    totalPages: number;
  }
}
