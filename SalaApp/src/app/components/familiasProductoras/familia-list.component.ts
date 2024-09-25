import { Component, OnInit } from '@angular/core';
import { FamiliaProductoraService, FamiliaProductora } from '../../services/familiaproductora.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ActivatedRoute } from '@angular/router';

@Component({
  imports: [CommonModule, RouterModule],
  standalone: true,
  selector: 'app-familia-list',
  templateUrl: './familia-list.component.html'
})
export class FamiliaListComponent implements OnInit {
  familias: FamiliaProductora[] = [];
  errorMessage: string = '';
  successMessage: string = '';

  constructor(private familiaService: FamiliaProductoraService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.successMessage = params['message'] || null;
    });
    this.familiaService.getFamiliasProductoras().subscribe(
      familias => {
        this.familias = familias;
      },
      error => {
        console.error('Error: ', error);
        this.errorMessage = error.error.message;
      }
    );
  }
}

