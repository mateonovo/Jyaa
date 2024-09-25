import { Component, inject } from "@angular/core";
import { RouterModule } from "@angular/router";
import { Router } from '@angular/router'
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { LoginService } from "../../services/login.service";

@Component({
    selector: 'app-navbar',
    standalone: true,
    imports: [RouterModule,FormsModule, CommonModule],
    templateUrl: './navbar.component.html'
  })

export class NavbarComponent{
  auth = inject(LoginService); 

  constructor(private router: Router) {}

  ngOnInit() {
    this.auth;
  }

  logout() {
    if (localStorage.getItem('token')) {
        localStorage.removeItem('token');
        this.router.navigate(['/login']);
  }
  
}

}