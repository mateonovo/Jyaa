import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RecetaService, Receta } from '../../services/receta.service';
import { CommonModule } from '@angular/common';


@Component({
  imports: [CommonModule], 
  standalone: true,
  selector: 'app-receta-detail',
  templateUrl: './receta-detail.component.html'
})


export class RecetaDetailComponent implements OnInit {
  receta: Receta | undefined;
  errorMessage: string = '';

  constructor(
    private route: ActivatedRoute,
    private recetaService: RecetaService
  ) {}

  ngOnInit() {
    const recetaId = Number(this.route.snapshot.paramMap.get('id'));
    if (recetaId) {
      this.recetaService.getReceta(recetaId).subscribe(
        receta => {        
          this.receta = receta;
        },
        error => {
          console.error('Error: ', error);
          this.errorMessage = error.error.message; 
        }
      );
    }
  }
}
