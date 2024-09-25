import { Component, OnInit } from '@angular/core';
import { LoteService, Lote } from '../../services/lote.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-delete-lote',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './delete-lote.component.html'
})

export class DeleteLoteComponent implements OnInit {
  lote: Lote = { activo: true, id: 0, codigo: '', nombre: '', fechaElaboracion: '', cantidadProducida: 0, materiaPrima: [], costoLote: 0 };
  errorMessage: string = '';
  successMessage: string = '';
  loteId: number | null = null;

  constructor(private loteService: LoteService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.loteId = +params['id'];
        this.loadLote(this.loteId);
      }
    });
  }

  loadLote(id: number) {
    this.loteService.getLote(id).subscribe(
      (lote) => {
        this.lote = lote;
      },
      (error) => {
        console.error('Error: ', error);
        this.errorMessage = 'No se pudo cargar el lote';
      }
    );
  }

  deleteLote() {
    if (this.loteId !== null) {
      this.loteService.deleteLote(this.loteId).subscribe(
        () => {
          this.successMessage = "Lote eliminado con éxito";
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
      this.errorMessage = 'ID de lote no válido';
    }
  }
}
