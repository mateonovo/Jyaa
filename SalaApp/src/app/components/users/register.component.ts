import { Component } from '@angular/core';
import { UserService, UsuarioRequest } from '../../services/user.service';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';


@Component({
  imports: [FormsModule, CommonModule],
  standalone: true,
  selector: 'app-register',
  templateUrl: './register.component.html'
})

export class RegisterComponent {
  user: UsuarioRequest = { email: '', nombre: '', apellido: '', password: '' };
  errorMessage: string = '';
  successMessage: string = '';

  constructor(private userService: UserService, private router: Router) {}

  register(registerForm: NgForm) {
    if (registerForm.valid) {
      this.userService.createUser(this.user).subscribe(
        response => {
          console.log('Usuario creado', response);
          this.successMessage = "Usuario creado con exito";
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
