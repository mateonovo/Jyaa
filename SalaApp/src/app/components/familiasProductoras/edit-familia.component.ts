import { Component, OnInit } from '@angular/core';
import { FamiliaProductoraService, FamiliaProductora } from '../../services/familiaproductora.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-edit-familia',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './edit-familia.component.html'
})

export class EditFamiliaComponent implements OnInit {
  familia: FamiliaProductora = { id:0, nombre: '', fechaInicio: '', zona: '' };
  errorMessage: string = '';
  successMessage: string = '';
  familiaId: number | null = null;

  constructor(private familiaProductoraService: FamiliaProductoraService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.familiaId = +params['id'];
        this.loadFamiliaProductora(this.familiaId);
      }
    });
  }

  loadFamiliaProductora(id: number) {
    this.familiaProductoraService.getFamiliaProductora(id).subscribe(
      (familia) => {
        this.familia = {
          id: familia.id,
          nombre: familia.nombre,
          fechaInicio: familia.fechaInicio,
          zona: familia.zona
        };
      },
      (error) => {
        console.error('Error: ', error);
        this.errorMessage = 'No se pudo cargar la familia productora';
      }
    );
  }

  saveFamilia(form: NgForm) {
    if (form.valid && this.familiaId !== null) {
      this.familiaProductoraService.updateFamiliaProductora(this.familia).subscribe(
        response => {
          console.log('Familia Productora actualizada', response);
          this.successMessage = "Familia Productora actualizada con éxito";
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
