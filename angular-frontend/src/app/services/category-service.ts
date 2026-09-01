import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { CategoryPage, RequestCategory, ResponseCategory } from '../../types/category-types';
import { BASE_URL } from '../base-url';


@Injectable({ providedIn: 'root' })
export class CategoryService {
  private http = inject(HttpClient);

  save(dto: RequestCategory): Observable<ResponseCategory> {
    return this.http.post<ResponseCategory>(`${BASE_URL}/category`, dto);
  }

  update(id: number, dto: RequestCategory): Observable<ResponseCategory> {
    return this.http.put<ResponseCategory>(`${BASE_URL}/category/${id}`, dto);
  }

  find(id: number): Observable<ResponseCategory> {
    return this.http.get<ResponseCategory>(`${BASE_URL}/category/${id}`);
  }

  findAll(page = 0, size = 20): Observable<CategoryPage> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);
    return this.http.get<CategoryPage>(`${BASE_URL}/category`, { params });
  }

  delete(id: number) {
    this.http.delete<void>(`${BASE_URL}/category/${id}`);
  }
}
