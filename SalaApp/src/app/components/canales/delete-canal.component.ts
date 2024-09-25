import { Component, OnInit } from '@angular/core';
import { CanalService, CanalRequest } from '../../services/canal.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

@Component({
    imports: [CommonModule, RouterModule],
    standalone: true,
    selector: 'app-canal-list',
    templateUrl: './delete-canal.component.html'
})

export class DeleteCanalComponent implements OnInit {

        canal : CanalRequest = {nombre: '', ubicacion: ''};
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
                this.canal = canal;
              },
              (error) => {
                console.error('Error: ', error);
                this.errorMessage = 'No se pudo cargar el canal';
              }
            );
          }

          deleteCanal() {
            if (this.canalId !== null) {
              this.canalService.deleteCanal(this.canalId).subscribe(
                () => {
                  this.successMessage = "Canal eliminado con éxito";
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
              this.errorMessage = 'ID de canal no válido';
            }
          }
}
    