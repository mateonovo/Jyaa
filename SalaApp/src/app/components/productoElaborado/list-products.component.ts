import { Component, OnInit } from '@angular/core';
import { ProductoService, Producto } from '../../services/producto.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';

@Component({
  imports: [CommonModule, RouterModule],
  standalone: true,
  selector: 'app-list-products',
  templateUrl: './list-products.component.html'
})
export class ProductListComponent implements OnInit {
  productos: Producto[] = [];
  filteredProductos: Producto[] = [];
  showActive: boolean = true;
  filterOption: string = 'active'; // 'all', 'active', 'inactive'
  successMessage: string = '';

  constructor(private route: ActivatedRoute, private productoService: ProductoService) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.successMessage = params['message'] || null;
    });
    this.productoService.getProductos().subscribe(
      productos => {
        this.productos = productos;
        this.filterProductos();
      },
      error => {
        console.error('Error: ', error);
      }
    );
  }

  filterProductos() {
    if (this.filterOption === 'active') {
      this.filteredProductos = this.productos.filter(producto => producto.activo);
    } else if (this.filterOption === 'inactive') {
      this.filteredProductos = this.productos.filter(producto => !producto.activo);
    } else {
      this.filteredProductos = this.productos;
    }
  }

  toggleFilter(filterOption: string) {
    this.filterOption = filterOption;
    this.filterProductos();
  }
}