import { Component, OnInit } from '@angular/core';
import { LoteService, Lote } from '../../services/lote.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ActivatedRoute } from '@angular/router';

@Component({
  imports: [CommonModule, RouterModule],
  standalone: true,
  selector: 'app-lote-list',
  templateUrl: './lote-list.component.html'
})
export class LoteListComponent implements OnInit {
  lotes: Lote[] = [];
  filteredLotes: Lote[] = [];
  filterOption: string = 'active'; // 'all', 'active', 'inactive'
  errorMessage: string = '';
  successMessage: string = '';

  constructor(private loteService: LoteService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.successMessage = params['message'] || null;
    });
    this.loteService.getLotes().subscribe(
      lotes => {
        this.lotes = lotes;
        console.log(this.lotes);
        this.filterLotes();
      },
      error => {
        console.error('Error: ', error);
        this.errorMessage = 'Error al cargar los lotes.';
      }
    );
  }

  filterLotes() {
    if (this.filterOption === 'active') {
      this.filteredLotes = this.lotes.filter(lote => lote.activo);
    } else if (this.filterOption === 'inactive') {
      this.filteredLotes = this.lotes.filter(lote => !lote.activo);
    } else {
      this.filteredLotes = this.lotes;
    }
    console.log("Lotes filtrados: " + this.filteredLotes)
  }

  toggleFilter(filterOption: string) {
    this.filterOption = filterOption;
    this.filterLotes();
  }
}
