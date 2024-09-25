import { Component, OnInit } from '@angular/core';
import { UserService, Usuario } from '../../services/user.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';

@Component({
  imports: [CommonModule, RouterModule],
  standalone: true,
  selector: 'app-user-list',
  templateUrl: './user-list.component.html'
})
export class UserListComponent implements OnInit {
  users: Usuario[] = [];
  errorMessage: string = '';
  successMessage: string = '';

  constructor(private userService: UserService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.successMessage = params['message'] || null;
    });
    this.userService.getUsers().subscribe(
      users => {
        this.users = users;
      },
      error => {
        console.error('Error: ', error);
        this.errorMessage = error.error.message;
      }
    );
  }
}
