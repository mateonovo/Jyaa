import { Component, OnInit } from '@angular/core';
import { RecetaService, Receta, RecetaRequest } from '../../services/receta.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-delete-receta',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './delete-receta.component.html'
})

export class DeleteRecetaComponent implements OnInit {
  receta: RecetaRequest = { nombre: '', texto: '' };
  errorMessage: string = '';
  successMessage: string = '';
  recetaId: number | null = null;

  constructor(private recetaService: RecetaService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.recetaId = +params['id'];
        this.loadReceta(this.recetaId);
      }
    });
  }

  loadReceta(id: number) {
    this.recetaService.getReceta(id).subscribe(
      (receta) => {
        this.receta.id = receta.id;
        this.receta.nombre = receta.nombre;
      },
      (error) => {
        console.error('Error: ', error);
        this.errorMessage = 'No se pudo cargar la receta';
      }
    );
  }

  deleteReceta() {
    if (this.recetaId !== null) {
      this.recetaService.deleteReceta(this.recetaId).subscribe(
        () => {
          this.successMessage = "Receta eliminada con éxito";
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
      this.errorMessage = 'ID de receta no válido';
    }
  }
}
