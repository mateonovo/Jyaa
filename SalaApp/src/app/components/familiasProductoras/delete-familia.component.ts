import { Component, OnInit } from '@angular/core';
import { FamiliaProductoraService, FamiliaProductora } from '../../services/familiaproductora.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-delete-familia',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './delete-familia.component.html'
})

export class DeleteFamiliaComponent implements OnInit {
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
        this.familia = familia;
      },
      (error) => {
        console.error('Error: ', error);
        this.errorMessage = 'No se pudo cargar la familia productora';
      }
    );
  }

  deleteFamilia() {
    if (this.familiaId !== null) {
      this.familiaProductoraService.deleteFamiliaProductora(this.familiaId).subscribe(
        () => {
          this.successMessage = "Familia Productora eliminada con éxito";
          this.router.navigate(['/familias'], { queryParams: { message: this.successMessage } });
        },
        (error) => {
          console.error('Error: ', error);
          this.errorMessage = error.error.message;
        }
      );
    } else {
      this.errorMessage = 'ID de familia no válido';
    }
  }
}
