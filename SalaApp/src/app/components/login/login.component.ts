import { Component } from '@angular/core';
import { LoginService, Usuario} from '../../services/login.service';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';


@Component({
  imports: [FormsModule, CommonModule],
  standalone: true,
  selector: 'app-login',
  templateUrl: './login.component.html'
})

export class LoginComponent {
  user: Usuario = { email: '', password: ''};
  errorMessage: string = '';
  successMessage: string = '';

  constructor(private loginService: LoginService, private router: Router) {}

  login(loginForm: NgForm) {
    this.loginService.login(this.user).subscribe(
        (response: any) => {
          localStorage.setItem('token', response.token);
          this.router.navigate(['/home']);
        
        if (response.token) {
            this.successMessage = 'Usuario logueado correctamente';
            this.router.navigate(['/']);
            }},
        error => {
          this.errorMessage = 'Usuario o contraseña incorrectos';
    }
  )}
}
