import { Component, OnInit } from '@angular/core';
import { ResponseCategory } from '../../../types/category-types';
import { ProductPage, ResponseProductDto } from '../../../types/product-types';
import { CategoryService } from '../../services/category-service';
import { ProductService } from '../../services/product-service';
import { ResponseSupplierDto } from './../../../types/supplier-types';
import { SupplierService } from './../../services/supplier-service';

@Component({
  imports: [],
  selector: 'app-product-form',
  styleUrl: './product-form.css',
  templateUrl: './product-form.html',
})
export class ProductForm implements OnInit {
  constructor(
    private readonly categoryService: CategoryService,
    private readonly supplierService: SupplierService,
    private readonly productService: ProductService
  ) { }

  categories: ResponseCategory[] = [];
  suppliers: ResponseSupplierDto[] = [];
  products: ResponseProductDto[] = [];

  productsPage: ProductPage = {
    content: [],
    page: { size: 0, number: 0, totalPages: 0, totalElements: 0 },
  };

  ngOnInit(): void {
    this.getCategories();
    this.getSuppliers();
    this.getProducts();
  }

  getCategories() {
    this.categoryService.findAll(0, 20).subscribe({
      next: (response) => {
        this.categories = response.content;
        console.log('Category API:', response.page.totalElements, 'itens');
      },
      error: (err) => console.error(err),
    });
  }

  getSuppliers() {
    this.supplierService.findAll(0, 20).subscribe({
      next: (response) => {
        this.suppliers = response.content;
        console.log('Supplier API:', response.page.totalElements, 'itens');
      },
      error: (err) => console.error(err),
    });
  }

  getProducts() {
    this.productService.findAll(0, 20).subscribe({
      next: (response) => {
        this.productsPage = response;
        this.products = response.content;
        console.log('Product API:', response.page.totalElements, 'itens');
      },
      error: (err) => console.error(err),
    });
  }
}
