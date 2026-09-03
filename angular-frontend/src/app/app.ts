import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CategoryForm } from "./page/category-form/category-form";
import { SupplierForm } from "./page/supplier-form/supplier-form";

@Component({
  imports: [RouterOutlet, CategoryForm, SupplierForm],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('angular-frontend');
}
