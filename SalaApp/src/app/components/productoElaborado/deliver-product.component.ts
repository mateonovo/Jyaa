import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ItemDeInsumo, ProductoService } from '../../services/producto.service';
import { Canal, CanalService } from '../../services/canal.service';

@Component({
  imports: [FormsModule, CommonModule],
  standalone: true,
  selector: 'app-entregar-producto',
  templateUrl: './deliver-product.component.html'
})
export class EntregarProductoComponent implements OnInit {
  productId: string = '';
  selectedCanal: number = 0;
  cantidad: number = 1
  canales: any[] = [];
  message: string | null = null;

  constructor(private route: ActivatedRoute, private canalesService: CanalService, private productoService: ProductoService, private router: Router) {}

  ngOnInit() {
    const productId = this.route.snapshot.paramMap.get('productoId');
    if (productId) {
      this.productId = productId;
      console.log(this.productId)
    } else {
      this.message = 'ID de producto no encontrado';
    }
    this.loadCanales();
  }

  loadCanales() {
    this.canalesService.getCanales().subscribe(
      response => {
        this.canales = response;
      },
      error => {
        console.error('Error al cargar canales', error);
      }
    );
  }

  entregarProducto(entregaForm: NgForm) {
    if (entregaForm.valid) {
          this.canalesService.entregarProductos(this.productId, this.selectedCanal, this.cantidad).subscribe(
            response => {
              console.log(this.productId)
              this.message = "Productos entregados";
              entregaForm.reset();
              this.selectedCanal = 1;
              this.cantidad = 1;
              this.router.navigate(['/productos'], { queryParams: { message: this.message } });
            },
            error => {
              console.log(this.productId)
              console.error('Error: ', error);
              this.message = error.error.message;
            }
          );     
    }
  
  }

}
