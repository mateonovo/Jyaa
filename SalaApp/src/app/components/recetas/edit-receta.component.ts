import { Component, OnInit } from '@angular/core';
import { RecetaService, Receta, RecetaRequest } from '../../services/receta.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-edit-receta',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './edit-receta.component.html'
})

export class EditRecetaComponent implements OnInit {
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
        this.receta.nombre = receta.nombre;
        this.receta.texto= receta.texto;
      },
      (error) => {
        console.error('Error: ', error);
        this.errorMessage = 'No se pudo cargar la receta';
      }
    );
  }

  saveReceta(form: NgForm) {
    if (form.valid && this.recetaId !== null) {
      this.recetaService.updateReceta(this.receta).subscribe(
        response => {
          console.log('Receta actualizada', response);
          this.successMessage = "Receta actualizada con éxito";
          this.router.navigate(['/recetas'], { queryParams: { message: this.successMessage } });
        },
        error => {
          console.error('Error: ', error);
          this.errorMessage = error.error.message;
        }
      );
    } else {
      this.errorMessage = 'Por favor, complete correctamente los campos';
    }
  }
}
