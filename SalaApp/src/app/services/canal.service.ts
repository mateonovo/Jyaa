import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../environments/environment';
import { Producto } from './producto.service';

export interface Canal {
  id: number;
  nombre: string;
  ubicacion: string;
}

export interface CanalRequest {
  nombre: string;
  ubicacion: string;
}

@Injectable({
  providedIn: 'root'
})

export class CanalService {
  private apiURL = `${environment.apiUrl}/canalesVenta`;
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' ,'Authorization':`Bearer ${localStorage.getItem('token')}`});
  constructor(private http: HttpClient) {
  }

  getCanales(): Observable<Canal[]> {
    return this.http.get<Canal[]>(`${this.apiURL}/all`, { headers: this.headers });
  }

  getCanal(id: number): Observable<CanalRequest> {
    return this.http.get<CanalRequest>(`${this.apiURL}/${id}`, { headers: this.headers });
  }


  deleteCanal(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiURL}/${id}`, { headers: this.headers })
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: any) {
    console.error('An error occurred', error.message);
    return throwError(error);
  }

  updateCanal(canal: CanalRequest, id: number): Observable<Canal> {
    return this.http.put<Canal>(`${this.apiURL}/${id}`, canal, { headers:this.headers })
      .pipe(
        catchError(this.handleError)
      );
  }

  createCanal(canal: CanalRequest): Observable<Canal> {
    return this.http.post<Canal>(`${this.apiURL}`, canal, { headers: this.headers })
      .pipe(
        catchError(this.handleError)
      );
  }

  entregarProductos(productoId: string, canalId: number, cantidad: number): Observable<any> {
    const url = `${this.apiURL}/${productoId}/agregarProducto`; 
    let params = new HttpParams()
      .set('idCanal', canalId.toString()) 
      .set('cantProductos', cantidad.toString()); 

    return this.http.post(url, {}, { params: params,  headers: this.headers }); 
    
  }

  getProductos(canalId: number): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.apiURL}/${canalId}/productos`, { headers: this.headers });
  }
}

