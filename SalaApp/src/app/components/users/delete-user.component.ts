import { Component, OnInit } from '@angular/core';
import { UserService, UsuarioRequest } from '../../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-delete-user',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './delete-user.component.html'
})

export class DeleteUserComponent implements OnInit {
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
        this.user = user;
      },
      (error) => {
        console.error('Error: ', error);
        this.errorMessage = 'No se pudo cargar el usuario';
      }
    );
  }

  deleteUser() {
    if (this.userId !== null) {
      this.userService.deleteUser(this.userId).subscribe(
        () => {
          this.successMessage = "Usuario eliminado con éxito";
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
      this.errorMessage = 'ID de usuario no válido';
    }
  }
}
