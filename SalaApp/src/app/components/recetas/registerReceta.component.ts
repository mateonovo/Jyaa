import { Component } from '@angular/core';
import { RecetaService, Receta, RecetaRequest } from '../../services/receta.service';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  imports: [FormsModule, CommonModule],
  standalone: true,
  selector: 'app-registerReceta',
  templateUrl: './registerReceta.component.html'
})

export class RegisterRecetaComponent {
  receta: RecetaRequest = { nombre: '', texto: '' };
  errorMessage: string = '';
  successMessage: string = '';

  constructor(private recetaService: RecetaService, private router: Router) {}

  register(registerForm: NgForm) {
    if (registerForm.valid) {
      this.recetaService.createReceta(this.receta).subscribe(
        response => {
          console.log('Receta creada', response);
          this.successMessage = "Receta creada con exito";
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

