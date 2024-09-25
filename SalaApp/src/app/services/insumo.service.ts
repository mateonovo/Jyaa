import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../environments/environment';

export interface Insumo {
  id?: number | null;
  nombre: string;
  cantidad: number;
  costoUnitario: string;
}

/*export interface FamiliaProductoraPost {
  id?: number;
  nombre: string;
  fecha_inicio: string;
  zona: string;
}*/

@Injectable({
  providedIn: 'root'
})

export class InsumoService {
  private apiURL = `${environment.apiUrl}/insumos`;

  private headers = new HttpHeaders({ 'Content-Type': 'application/json' ,'Authorization':`Bearer ${localStorage.getItem('token')}`});
  constructor(private http: HttpClient) {
  }

  
  createInsumo(insumo: Insumo): Observable<Insumo> {
    return this.http.post<Insumo>(this.apiURL, insumo, { headers:this.headers })
      .pipe(
        catchError(this.handleError)
      );
  }

  getInsumo(id: number): Observable<Insumo> {
    return this.http.get<Insumo>(`${this.apiURL}/${id}`, { headers: this.headers });
  }

  getInsumos(): Observable<Insumo[]> {
    
    return this.http.get<Insumo[]>(`${this.apiURL}/all`, { headers: this.headers });
  }


  updateInsumo(insumo: Insumo): Observable<Insumo> {
    return this.http.put<Insumo>(this.apiURL, insumo, { headers:this.headers })
      .pipe(
        catchError(this.handleError)
      );
  }

  deleteInsumo(id: number): Observable<void> {
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
