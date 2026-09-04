import { HttpClient, HttpParams } from '@angular/common/http';
import { Service, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { ProductPage, RequestProductDto, ResponseProductDto } from '../../types/product-types';
import { BASE_URL } from '../base-url';


@Service()
export class ProductService {
  private http = inject(HttpClient);

  save(dto: RequestProductDto): Observable<ResponseProductDto> {
    return this.http.post<ResponseProductDto>(`${BASE_URL}/product`, dto);
  }

  update(id: number, dto: RequestProductDto): Observable<ResponseProductDto> {
    return this.http.put<ResponseProductDto>(`${BASE_URL}/product/${id}`, dto);
  }

  find(id: number): Observable<ResponseProductDto> {
    return this.http.get<ResponseProductDto>(`${BASE_URL}/product/${id}`);
  }

  findAll(page = 0, size = 20): Observable<ProductPage> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);
    return this.http.get<ProductPage>(`${BASE_URL}/product`, { params });
  }

  delete(id: number) {
    return this.http.delete<void>(`${BASE_URL}/product/${id}`);
  }
}
