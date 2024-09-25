import { Component, OnInit } from '@angular/core';
import { MateriaPrimaService, MateriaPrimaPost } from '../../services/materiaPrima.service';
import { CommonModule } from '@angular/common';
import { FamiliaProductoraService, FamiliaProductora } from '../../services/familiaproductora.service';
import { Router } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';

@Component({
    imports: [FormsModule, CommonModule],
    standalone: true,
    selector: 'app-materia-create',
    templateUrl: './create-materia.component.html'
    })
export class CreateMateriaComponent {
    materia : MateriaPrimaPost = {nombre: '', fechaCompra: '', fechaVencimiento: '', costoPorKg: 0, formaAlmacenamiento: '', peso: 0 ,nombreProductor: ''};
    familias: FamiliaProductora[] = [];
    errorMessage: string = '';
    successMessage: string = '';

    constructor(private materiaService: MateriaPrimaService, private familiaService: FamiliaProductoraService, private router: Router) { }


    ngOnInit() {
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

   
    createMateria(form: NgForm) {
        this.materiaService.createMateriaPrima(this.materia).subscribe(
        response => {
            console.log('Materia prima creada', response);
            this.successMessage = "Materia prima creada con exito";
            this.router.navigate(['/materiasPrimas'], { queryParams: { message: this.successMessage } });
        },
        error => {
            console.log(this.materia.nombreProductor)
            console.error('Error: ', error);
            this.errorMessage = error.error.message;
        }
        );
    }
}

