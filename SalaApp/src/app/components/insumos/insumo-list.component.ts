import { Component, OnInit } from '@angular/core';
import { InsumoService, Insumo } from '../../services/insumo.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ActivatedRoute } from '@angular/router';

@Component({
  imports: [CommonModule, RouterModule],
  standalone: true,
  selector: 'app-insumo-list',
  templateUrl: './insumo-list.component.html'
})
export class InsumoListComponent implements OnInit {
  insumos: Insumo[] = [];
  errorMessage: string = '';
  successMessage: string = '';

  constructor(private insumoService: InsumoService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.successMessage = params['message'] || null;
    });
    this.insumoService.getInsumos().subscribe(
      insumos => {
        this.insumos = insumos;
      },
      error => {
        console.error('Error: ', error);
        this.errorMessage = error.error.message;
      }
    );
  }
}
