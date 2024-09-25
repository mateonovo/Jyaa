import { Component, OnInit } from '@angular/core';
import { CanalService } from '../../services/canal.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Producto } from '../../services/producto.service';


@Component({
  imports: [CommonModule, RouterModule],
  standalone: true,
  selector: 'app-canal-products',
  templateUrl: './canal-products.component.html'
})
export class CanalProductsComponent implements OnInit {
  productos: Producto[] = [];
  errorMessage: string = '';

  constructor(private canalService: CanalService, private route: ActivatedRoute) {}

  ngOnInit() {
    const canalId = Number(this.route.snapshot.paramMap.get('id'));
    if (canalId) {
        this.canalService.getProductos(canalId).subscribe(
            productos => {
            this.productos = productos;
            console.log(this.productos)
            },
            error => {
            console.error('Error: ', error);
            }
        );
    }
  }
}
