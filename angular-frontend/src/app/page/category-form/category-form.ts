import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RequestCategory } from '../../../types/category-types';
import { CategoryService } from '../../services/category-service';

@Component({
  imports: [FormsModule],
  standalone: true,
  selector: 'app-category-form',
  styleUrl: './category-form.css',
  templateUrl: './category-form.html',
})
export class CategoryForm {

  constructor(private readonly categoryService: CategoryService) { }

  name = '';
  description = '';
  id = 0;

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
