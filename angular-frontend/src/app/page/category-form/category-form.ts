import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CategoryPage, RequestCategory, ResponseCategory } from '../../../types/category-types';
import { CategoryService } from '../../services/category-service';
import { CommonModule } from '@angular/common';

@Component({
  imports: [FormsModule, CommonModule],
  standalone: true,
  selector: 'app-category-form',
  styleUrl: './category-form.css',
  templateUrl: './category-form.html',
})
export class CategoryForm implements OnInit {
  constructor(private readonly categoryService: CategoryService) {}

  name = '';
  description = '';
  id = 0;
  categories: ResponseCategory[] = [];

  page: CategoryPage = {
    content: [],
    page: { size: 0, number: 0, totalPages: 0, totalElements: 0 },
  };

  ngOnInit(): void {
    this.carregarCategorias();
  }

  carregarCategorias() {
    this.categoryService.findAll(0, 20).subscribe({
      next: (response) => {
        this.page = response;
        this.categories = response.content;
        console.log('API:', response.page.totalElements, 'itens');
      },
      error: (err) => console.error(err),
    });
  }

  salvar() {
    const request: RequestCategory = {
      name: this.name,
      description: this.description,
    };

    this.categoryService.save(request).subscribe({
      next: (res) => {
        console.log('Salvo:', res);
        this.name = '';
        this.description = '';
        this.carregarCategorias(); // <--- ESSA LINHA FALTAVA
      },
      error: (err) => console.error('Erro:', err),
    });
  }
}
