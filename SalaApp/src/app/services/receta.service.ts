import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Usuario } from './user.service';
import { environment } from '../environments/environment';

export interface Receta {
  id: number;
  nombre: string;
  texto: string;
  usuario: Usuario;
}

export interface RecetaRequest {
  id?: number;
  nombre: string;
  texto: string;
}

@Injectable({
  providedIn: 'root'
})

export class RecetaService {
  private apiURL = `${environment.apiUrl}/recetas`;
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' ,'Authorization':`Bearer ${localStorage.getItem('token')}`});

  constructor(private http: HttpClient) {
  }

  
  createReceta(receta: RecetaRequest): Observable<RecetaRequest> {
    return this.http.post<RecetaRequest>(this.apiURL, receta, { headers: this.headers })
      .pipe(
        catchError(this.handleError)
      );
  }

  getReceta(id: number): Observable<Receta> {
    return this.http.get<Receta>(`${this.apiURL}/${id}`, { headers: this.headers });
  }

  getRecetas(): Observable<Receta[]> {
    return this.http.get<Receta[]>(`${this.apiURL}/all`, { headers: this.headers });
  }


  updateReceta(receta: RecetaRequest): Observable<RecetaRequest> {
    return this.http.put<RecetaRequest>(this.apiURL, receta, { headers: this.headers })
      .pipe(
        catchError(this.handleError)
      );
  }

  deleteReceta(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiURL}/${id}`, { headers: this.headers })
      .pipe(
        catchError(this.handleError)
      );
  }


  private handleError(error: any) {
    console.error('An error occurred', error.message);
    return throwError(error);
  }
}
