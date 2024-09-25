import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Producto, ProductoService } from '../../services/producto.service';

@Component({
  selector: 'app-delete-producto',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './delete-producto.component.html'
})

export class DeleteProductoComponent implements OnInit {
  producto: Producto | undefined;
  errorMessage: string = '';
  successMessage: string = '';
  productoId: number | null = null;

  constructor(private productoService: ProductoService, private route: ActivatedRoute, private router: Router) {

   }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.productoId = +params['id'];
        this.loadProducto(this.productoId);
      }
    });
  }

  loadProducto(id: number) {
    this.productoService.getProducto(id).subscribe(
      (producto) => {
        this.producto = producto;
      },
      (error) => {
        console.error('Error: ', error);
        this.errorMessage = 'No se pudo cargar el usuario';
      }
    );
  }

  deleteProducto() {
    if (this.productoId !== null) {
      this.productoService.deleteProducto(this.productoId).subscribe(
        () => {
          this.successMessage = "Producto eliminado con éxito";
          setTimeout(() => {
            this.router.navigate(['/']);
          }, 2000);
        },
        (error) => {
          console.error('Error: ', error);
          this.errorMessage = error.error.message;
        }
      );
    } else {
      this.errorMessage = 'ID de usuario no válido';
    }
  }
}
