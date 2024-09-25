import { Component, OnInit } from '@angular/core';
import { MateriaPrimaService, MateriaPrima,MateriaPrimaPost } from '../../services/materiaPrima.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Route, Router, RouterModule } from '@angular/router';
import { NgForm, FormsModule } from '@angular/forms';
import { FamiliaProductoraService, FamiliaProductora } from '../../services/familiaproductora.service';

@Component({
    imports: [FormsModule, CommonModule],
    standalone: true,
    selector: 'app-edit-materia',
    templateUrl: './edit-materia.component.html'
})

export class EditMateriaComponent implements OnInit {
    materia : MateriaPrimaPost = {nombre: '',fechaCompra: '', fechaVencimiento: '', costoPorKg: 0, formaAlmacenamiento: '', peso: 0 ,nombreProductor: ''};
    errorMessage: string = '';
    successMessage: string = '';
    materiaId: number | null = null;
    familias: FamiliaProductora[] = [];

    constructor(private materiaService: MateriaPrimaService, private route: ActivatedRoute, private familiaService: FamiliaProductoraService, private router: Router) { }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
          if (params['id']) {
            this.materiaId = +params['id'];
            this.loadMateria(this.materiaId);
          }
        });
        this.familiaService.getFamiliasProductoras().subscribe(
            familias => {
                this.familias = familias;
            },
            error => {
                console.error('Error: ', error);
                this.errorMessage = error.error.message;
            }
      )}


        loadMateria(id: number) {
            this.materiaService.getMateriaPrima(id).subscribe(
            (materia) => {
                this.materia = {
                nombre: materia.nombre,
                fechaCompra: materia.fechaCompra,
                fechaVencimiento: materia.fechaVencimiento,
                costoPorKg: materia.costoPorKg,
                formaAlmacenamiento: materia.formaAlmacenamiento,
                peso: materia.peso,
                nombreProductor: materia.productor.nombre
                };
            },
            (error) => {
                console.error('Error: ', error);
                this.errorMessage = 'No se pudo cargar la materia prima';
            }
            );
        }

        saveMateria(form: NgForm) {
            if (form.valid && this.materiaId !== null) {
            this.materiaService.updateMateriaPrima(this.materia, this.materiaId).subscribe(
                response => {
                console.log('Materia prima actualizada', response);
                this.successMessage = "Materia prima actualizada con éxito";
                this.router.navigate(['/materiasPrimas'], { queryParams: { message: this.successMessage } });
                },
                error => {
                console.error('Error: ', error);
                this.errorMessage = error.error.message;
                }
            );
            } else {
            this.errorMessage = 'ID de materia prima no válido';
        }

    }
    
}    


