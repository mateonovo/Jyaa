import { Component } from '@angular/core';
import { FamiliaProductoraService, FamiliaProductora } from '../../services/familiaproductora.service';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  imports: [FormsModule, CommonModule],
  standalone: true,
  selector: 'app-registerFamilia',
  templateUrl: './registerFamilia.component.html'
})

export class RegisterFamiliaComponent {
  familia: FamiliaProductora = { id: 0, nombre: '', fechaInicio: '', zona: '' };
  errorMessage: string = '';
  successMessage: string = '';

  constructor(private familiaService: FamiliaProductoraService, private router: Router) {}

  register(registerForm: NgForm) {
    if (registerForm.valid) {
      console.log(this.familia.id + ' ' + this.familia.fechaInicio)
      this.familiaService.createFamiliaProductora(this.familia).subscribe(
        response => {
          console.log('Familia Productora creada', response);
          this.successMessage = "Familia Productora creada con exito";
          this.router.navigate(['/familias'], { queryParams: { message: this.successMessage } });
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

