import { Routes } from '@angular/router';
import { CategoryForm } from './page/category-form/category-form';
import { ProductForm } from "./page/product-form/product-form";
import { SupplierForm } from "./page/supplier-form/supplier-form";

export const routes: Routes = [
  {
    path: 'fornecedores',
    component: SupplierForm,
  },
  {
    path: 'categorias',
    component: CategoryForm,
  },
  {
    path: 'produtos',
    component: ProductForm,
  },
  {
    path: '',
    redirectTo: 'produtos',
    pathMatch: 'full'
  }
];
