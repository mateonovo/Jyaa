import { Component, OnInit } from '@angular/core';
import { MateriaPrimaService, MateriaPrimaRequest } from '../../services/materiaPrima.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';


@Component({
    imports: [CommonModule, RouterModule],
    standalone: true,
    selector: 'app-materia-delete',
    templateUrl: './delete-materia.component.html'
    })

export class MateriaDeleteComponent implements OnInit {
    materia: MateriaPrimaRequest = {nombre: '',fechaCompra: '', fechaVencimiento: '', costoPorKg: 0, formaAlmacenamiento: '', peso: 0, productor: {id: 0, nombre: '', fechaInicio: '', zona: ''},nombreProductor: ''};
    errorMessage: string = '';
    successMessage: string = '';
    materiaId: number | null = null;

    constructor(private materiaService: MateriaPrimaService, private route: ActivatedRoute, private router: Router) {}

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            if (params['id']) {
                this.materiaId = +params['id'];
                this.loadMateria(this.materiaId);
            }
        });
    }
    loadMateria(id: number) {
        this.materiaService.getMateriaPrima(id).subscribe(
            (materia) => {
                this.materia = materia;
            },
            (error) => {
                console.error('Error: ', error);
                this.errorMessage = 'No se pudo cargar la materia prima';
            }
        );
    }

    deleteMateria() {
        if (this.materiaId !== null) {
            this.materiaService.deleteMateriaPrima(this.materiaId).subscribe(
                () => {
                    this.successMessage = "Materia prima eliminada con éxito";
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
            this.errorMessage = 'ID de materia prima no válido';
        }
    }
}

