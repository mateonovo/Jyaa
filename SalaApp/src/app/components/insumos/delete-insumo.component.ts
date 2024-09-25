import { Component, OnInit } from '@angular/core';
import { InsumoService, Insumo } from '../../services/insumo.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-delete-insumo',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './delete-insumo.component.html'
})

export class DeleteInsumoComponent implements OnInit {
  insumo: Insumo = { nombre: '', cantidad: 0, costoUnitario: '0' };
  errorMessage: string = '';
  successMessage: string = '';
  insumoId: number | null = null;

  constructor(private insumoService: InsumoService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.insumoId = +params['id'];
        this.loadInsumo(this.insumoId);
      }
    });
  }

  loadInsumo(id: number) {
    this.insumoService.getInsumo(id).subscribe(
      (insumo) => {
        this.insumo = insumo;
      },
      (error) => {
        console.error('Error: ', error);
        this.errorMessage = 'No se pudo cargar el insumo';
      }
    );
  }

  deleteInsumo() {
    if (this.insumoId !== null) {
      this.insumoService.deleteInsumo(this.insumoId).subscribe(
        () => {
          this.successMessage = "Insumo eliminado con éxito";
          this.router.navigate(['/insumos'], { queryParams: { message: this.successMessage } });

        },
        (error) => {
          console.error('Error: ', error);
          this.errorMessage = error.error.message;
        }
      );
    } else {
      this.errorMessage = 'ID de insumo no válido';
    }
  }
}
