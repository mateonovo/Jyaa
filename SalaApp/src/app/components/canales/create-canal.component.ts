import { Component, OnInit } from '@angular/core';
import { CanalService, Canal,CanalRequest } from '../../services/canal.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { NgForm, FormsModule } from '@angular/forms';



@Component({
  selector: 'app-create-canal',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './create-canal.component.html'
})

export class CreateCanalComponent {
  canal : CanalRequest = {nombre: '',ubicacion: '' };
  errorMessage: string = '';
  successMessage: string = '';
  canalId: number | null = null;

  constructor(private canalService: CanalService, private router: Router) { }

createCanal(form: NgForm) {
    if (form.valid) {
        this.canalService.createCanal(this.canal).subscribe(
        response => {
            console.log('Canal creado', response);
            this.successMessage = "Canal creado con exito";
            this.router.navigate(['/canales'], { queryParams: { message: this.successMessage } });
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