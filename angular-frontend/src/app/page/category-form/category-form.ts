import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RequestCategory, ResponseCategory } from '../../../types/category-types';
import { CategoryService } from '../../services/category-service';

@Component({
  imports: [FormsModule],
  standalone: true,
  selector: 'app-category-form',
  styleUrl: './category-form.css',
  templateUrl: './category-form.html',
})
export class CategoryForm implements OnInit {

  constructor(private readonly categoryService: CategoryService) { }

  ngOnInit(): void {
    this.carregarCategorias();
  }

  name = '';
  description = '';
  id = 0;

  categorias: ResponseCategory[] = [];

  carregarCategorias() {
    this.categoryService.findAll().subscribe({
      next: (response) => {
        // se seu backend retorna Page do Spring: { content: [], totalElements... }
        this.categorias = response.content;
      },
      error: (err) => console.error(err)
    });
  }


  salvar() {
    const request: RequestCategory = {
      name: this.name,
      description: this.description
    };

    this.categoryService.save(request).subscribe({
      next: (res) => {
        console.log('Salvo:', res);
        this.name = '';
        this.description = '';
      },
      error: (err) => console.error('Erro:', err)
    });
  }
}
