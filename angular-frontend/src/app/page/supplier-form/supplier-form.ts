import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ResponseSupplierDto, SupplierPage } from '../../../types/supplier-types';
import { SupplierService } from '../../services/supplier-service';

@Component({
  imports: [FormsModule,],
  selector: 'app-supplier-form',
  styleUrl: './supplier-form.css',
  templateUrl: './supplier-form.html',
})
export class SupplierForm implements OnInit {
  constructor(private readonly supplierService: SupplierService) { }
  onEdit = false;
  id = 0;

  name: string = '';
  cnpj: string = '';
  contactName: string = '';
  phoneNumber: string = '';
  email: string = '';
  address: string = '';
  city: string = '';
  state: string = '';
  suppliers: ResponseSupplierDto[] = [];

  page: SupplierPage = {
    content: [],
    page: { size: 0, number: 0, totalPages: 0, totalElements: 0 },
  };

  ngOnInit(): void {
    this.getSuppliers();
  }

  getSuppliers() {
    this.supplierService.findAll(0, 20).subscribe({
      next: (response) => {
        this.page = response;
        this.suppliers = response.content;
        console.log('Supplier API:', response.page.totalElements, 'itens');
      },
      error: (err) => console.error(err),
    });
  }

  saveSupplier() {
    const supplierDto = {
      name: this.name,
      cnpj: this.cnpj,
      contactName: this.contactName,
      phoneNumber: this.phoneNumber,
      email: this.email,
      address: this.address,
      city: this.city,
      state: this.state,
    };

    if (!this.onEdit) {
      this.supplierService.save(supplierDto).subscribe({
        next: (response) => {
          console.log('Supplier saved:', response);
          this.getSuppliers();
          this.clearForm();
        },
        error: (err) => console.error(err),
      });
    }
    else {
      this.supplierService.update(this.id, supplierDto).subscribe({
        next: (response) => {
          console.log('Supplier updated:', response);
          this.getSuppliers();
          this.clearForm();
          this.onEdit = false;
        },
        error: (err) => console.error(err),
      });
    }
  }

  clearForm() {
    this.name = '';
    this.cnpj = '';
    this.contactName = '';
    this.phoneNumber = '';
    this.email = '';
    this.address = '';
    this.city = '';
    this.state = '';
    this.id = 0;
    console.log('Form cleared');
  }

  findSupplierById(id: number) {
    this.supplierService.find(id).subscribe({
      next: (response) => {
        console.log('Supplier found:', response);
        this.name = response.name;
        this.cnpj = response.cnpj;
        this.contactName = response.contactName;
        this.phoneNumber = response.phoneNumber;
        this.email = response.email;
        this.address = response.address;
        this.city = response.city;
        this.state = response.state;
        this.onEdit = true;
        this.id = response.id;
      },
      error: (err) => console.error(err),
    });
  }

  removeSupplier(id: number) {
    this.supplierService.delete(id).subscribe({
      next: (response) => {
        console.log('Supplier removed:', response);
        this.getSuppliers();
      },
      error: (err) => console.error(err),
    });
  }


}
