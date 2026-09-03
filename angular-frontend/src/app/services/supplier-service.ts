import { HttpClient, HttpParams } from '@angular/common/http';
import { Service, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { RequestSupplierDto, ResponseSupplierDto, SupplierPage } from '../../types/supplier-types';
import { BASE_URL } from '../base-url';

@Service()
export class SupplierService {
  private http = inject(HttpClient);

  save(dto: RequestSupplierDto): Observable<ResponseSupplierDto> {
    return this.http.post<ResponseSupplierDto>(`${BASE_URL}/supplier`, dto);
  }

  update(id: number, dto: RequestSupplierDto): Observable<ResponseSupplierDto> {
    return this.http.put<ResponseSupplierDto>(`${BASE_URL}/supplier/${id}`, dto);
  }

  find(id: number): Observable<ResponseSupplierDto> {
    return this.http.get<ResponseSupplierDto>(`${BASE_URL}/supplier/${id}`);
  }

  findAll(page = 0, size = 20): Observable<SupplierPage> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);
    return this.http.get<SupplierPage>(`${BASE_URL}/supplier`, { params });
  }

  delete(id: number) {
   return this.http.delete<void>(`${BASE_URL}/supplier/${id}`);
  }
}
