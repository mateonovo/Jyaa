import { Component, OnInit } from '@angular/core';
import { UserService, UsuarioRequest } from '../../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-edit-user',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './edit-user.component.html'
})

export class EditUserComponent implements OnInit {
  user: UsuarioRequest = { email: '', nombre: '', apellido: '', password: '' };
  errorMessage: string = '';
  successMessage: string = '';
  userId: number | null = null;

  constructor(private userService: UserService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.userId = +params['id'];
        this.loadUser(this.userId);
      }
    });
  }

  loadUser(id: number) {
    this.userService.getUser(id).subscribe(
      (user) => {
        this.user = {
          email: user.email,
          nombre: user.nombre,
          apellido: user.apellido,
          password: user.password
        };
      },
      (error) => {
        console.error('Error: ', error);
        this.errorMessage = 'No se pudo cargar el usuario';
      }
    );
  }

  saveUser(form: NgForm) {
    if (form.valid && this.userId !== null) {
      this.userService.updateUser(this.user, this.userId).subscribe(
        response => {
          console.log('Usuario actualizado', response);
          this.successMessage = "Usuario actualizado con éxito";
          this.router.navigate(['/users'], { queryParams: { message: this.successMessage } });
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
