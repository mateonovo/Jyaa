import { Component } from '@angular/core';
import { LoteService, LoteRequest } from '../../services/lote.service';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MateriaPrima, MateriaPrimaService } from '../../services/materiaPrima.service';
import { Router } from '@angular/router';

@Component({
  imports: [FormsModule, CommonModule],
  standalone: true,
  selector: 'app-register-lote',
  templateUrl: './register-lote.component.html'
})

export class RegisterLoteComponent {
  lote: LoteRequest = { activo: true, codigo: '', nombre: '', fechaElaboracion: '', cantidadProducida: 0, itemsDeMateriaPrima: [] };
  materiasPrimas: MateriaPrima[] = [];
  errorMessage: string = '';
  successMessage: string = '';

  constructor(private loteService: LoteService, private materiaPrimaService: MateriaPrimaService, private router: Router) {}
  
  ngOnInit(): void {
    this.materiaPrimaService.getMateriasPrimas().subscribe(
      data => {
        this.materiasPrimas = data;
      },
      error => {
        console.error('Error al obtener materias primas', error);
      }
    );
  }

  agregarItemDeMateriaPrima() {
    this.lote.itemsDeMateriaPrima.push({ materiaPrimaId: 0, cantidadEnKg: 0 });
  }

  eliminarItemDeMateriaPrima(index: number) {
    this.lote.itemsDeMateriaPrima.splice(index, 1);
  }

  register(registerForm: NgForm) {
    if (registerForm.valid) {
      this.loteService.createLote(this.lote).subscribe(
        response => {
          console.log('Lote creado', response);
          this.successMessage = "Lote creado con éxito";
          registerForm.resetForm();
          this.lote.itemsDeMateriaPrima = []; // Reseteamos los items
          this.router.navigate(['/lotes'], { queryParams: { message: this.successMessage } });
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

