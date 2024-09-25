import { Component, OnInit } from '@angular/core';
import { CanalService, Canal,CanalRequest } from '../../services/canal.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { NgForm, FormsModule } from '@angular/forms';


@Component({
    imports: [FormsModule, CommonModule],
    standalone: true,
    selector: 'app-edit-canal',
    templateUrl: './edit-canal.component.html'
})

export class EditCanalComponent implements OnInit {
    canal : CanalRequest = {nombre: '',ubicacion: '' };
    errorMessage: string = '';
    successMessage: string = '';
    canalId: number | null = null;

    constructor(private canalService: CanalService, private route: ActivatedRoute, private router: Router) { }


    ngOnInit(): void {
        this.route.params.subscribe(params => {
          if (params['id']) {
            this.canalId = +params['id'];
            this.loadCanal(this.canalId);
          }
        });
      }
      loadCanal(id: number) {
        this.canalService.getCanal(id).subscribe(
          (canal) => {
            this.canal = {
              nombre: canal.nombre,
              ubicacion: canal.ubicacion
            };
          },
          (error) => {
            console.error('Error: ', error);
            this.errorMessage = 'No se pudo cargar el canal';
          }
        );
      }

      saveCanal(form: NgForm) {
        if (form.valid && this.canalId !== null) {
          this.canalService.updateCanal(this.canal, this.canalId).subscribe(
            response => {
              console.log('Canal actualizado', response);
              this.successMessage = "Canal actualizado con éxito";
              this.router.navigate(['/canales'], { queryParams: { message: this.successMessage } });
            },
            error => {
              console.error('Error: ', error);
              this.errorMessage = error.error.message;
            }
          );
        } else {
          this.errorMessage = 'ID de canal no válido';
        }
      }
      


    }      
