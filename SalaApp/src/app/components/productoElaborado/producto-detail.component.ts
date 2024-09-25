import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { UserService, UsuarioRequest } from '../../services/user.service';
import { CommonModule } from '@angular/common';
import { Producto, ProductoService } from '../../services/producto.service';


@Component({
  imports: [CommonModule, RouterModule], 
  standalone: true,
  selector: 'app-producto-detail',
  templateUrl: './producto-detail.component.html'
})


export class ProductoDetailComponent implements OnInit {
    producto: Producto | undefined;
    errorMessage: string = '';
  
    constructor(
      private route: ActivatedRoute,
      private productoService: ProductoService
    ) {}
  
    ngOnInit() {
      const productoId = Number(this.route.snapshot.paramMap.get('id'));
      if (productoId) {
        this.productoService.getProducto(productoId).subscribe(
          producto => {        
            this.producto = producto;
          },
          error => {
            console.error('Error: ', error);
            this.errorMessage = error.error.message; 
          }
        );
      }
    }
}