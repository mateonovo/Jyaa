import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService, UsuarioRequest } from '../../services/user.service';
import { CommonModule } from '@angular/common';


@Component({
  imports: [CommonModule], 
  standalone: true,
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html'
})


export class UserDetailComponent implements OnInit {
  user: UsuarioRequest | undefined;
  errorMessage: string = '';

  constructor(
    private route: ActivatedRoute,
    private userService: UserService
  ) {}

  ngOnInit() {
    const userId = Number(this.route.snapshot.paramMap.get('id'));
    if (userId) {
      this.userService.getUser(userId).subscribe(
        user => {        
          this.user = user;
        },
        error => {
          console.error('Error: ', error);
          this.errorMessage = error.error.message; 
        }
      );
    }
  }
}
