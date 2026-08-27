import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { RequestCategory, ResponseCategory } from '../../types/category-types';
import { BASE_URL } from '../base-url';


@Injectable({ providedIn: 'root' })
export class CategoryService {
  private http = inject(HttpClient);

  save(dto: RequestCategory): Observable<ResponseCategory> {
    return this.http.post<ResponseCategory>(`${BASE_URL}/category`, dto);
  }
}
