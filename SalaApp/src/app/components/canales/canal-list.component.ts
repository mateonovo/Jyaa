import { Component, OnInit } from '@angular/core';
import { CanalService, Canal } from '../../services/canal.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ActivatedRoute } from '@angular/router';



@Component({
  imports: [CommonModule, RouterModule],
  standalone: true,
  selector: 'app-canal-list',
  templateUrl: './canal-detail.component.html'
})
export class CanalListComponent implements OnInit {
  canales: Canal[] = [];
  errorMessage: string = '';
  successMessage: string = '';

  constructor(private canalService: CanalService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.successMessage = params['message'] || null;
    });
    this.canalService.getCanales().subscribe(
      canales => {
        this.canales = canales;
      },
      error => {
        console.error('Error: ', error);
      }
    );
  }
}



