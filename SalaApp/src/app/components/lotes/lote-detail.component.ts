import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LoteService, Lote, ItemDeMateriaPrimaDetailed } from '../../services/lote.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  imports: [CommonModule, RouterModule],
  standalone: true,
  selector: 'app-lote-detail',
  templateUrl: './lote-detail.component.html'
})
export class LoteDetailComponent implements OnInit {
  lote: Lote | undefined;
  errorMessage: string = '';

  constructor(
    private route: ActivatedRoute,
    private loteService: LoteService
  ) {}

  ngOnInit() {
    const loteId = Number(this.route.snapshot.paramMap.get('id'));
    if (loteId) {
      this.loteService.getLote(loteId).subscribe(
        lote => {        
          this.lote = lote;
        },
        error => {
          console.error('Error: ', error);
          this.errorMessage = error.error.message; 
        }
      );
    }
  }

  getMateriaPrimaNombre(item: ItemDeMateriaPrimaDetailed): string {
    return item.materiaPrima ? item.materiaPrima.nombre : 'Desconocido';
  }
}
