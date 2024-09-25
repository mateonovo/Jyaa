import { Component } from '@angular/core';
import { ProductoService, Producto, ProductoTerminadoRequest } from '../../services/producto.service';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { Insumo, InsumoService } from '../../services/insumo.service';

@Component({
  imports: [FormsModule, CommonModule],
  standalone: true,
  selector: 'app-create-product',
  templateUrl: './create-product.component.html'
})

export class CreateProductComponent {
  producto: ProductoTerminadoRequest = { nombre: '', fechaEnvasado: '', fechaVencimiento: '', precioVenta: 0,
   cantidadProductos: 0, insumos: []}
  insumos: Insumo[] = [];
  errorMessage: string = '';
  successMessage: string = '';
  loteId: string | null = null;

  constructor(
    private productoService: ProductoService,
    private insumoService: InsumoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    this.loteId = this.route.snapshot.paramMap.get('id');
    this.insumoService.getInsumos().subscribe(
      data => {
        this.insumos = data;
      },
      error => {
        console.error('Error al obtener los insumos:', error);
      }
    );
  }

  agregarItemDeInsumo() {
    this.producto.insumos.push({ insumo: 0, cantidad: 0 });
  }

  eliminarItemDeInsumo(index: number) {
    this.producto.insumos.splice(index, 1);
  }

  registerProduct(productForm: NgForm) {
    if (productForm.valid && this.loteId) {
      this.productoService.createProduct(this.loteId, this.producto).subscribe(
        response => {
          this.successMessage = 'Producto creado con éxito';
          productForm.resetForm();
          this.producto.insumos = []; 
          this.router.navigate(['/productos']);
        },
        error => {
          console.error('Error: ', error);
          this.errorMessage = error.error.message;
        }
      );
    } else {
      this.errorMessage = 'Por favor, complete correctamente los campos';
    }
  }

  getInsumoNombre(insumoId: number): string {
    const insumo = this.insumos.find(i => i.id === insumoId);
    return insumo ? insumo.nombre : 'Desconocido';
  }
}