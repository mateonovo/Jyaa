import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../environments/environment';


export interface Producto {
  id: number,
  nombre: string;
  activo: boolean;
  fechaEnvasado: string;
  fechaVencimiento: string;
  precioVenta: number;
  cantidadProductos: number;
  costoTotal: number;
  insumos: ItemDeInsumo[];
}

export interface ProductoTerminadoRequest {
  nombre: string;
  fechaEnvasado: string;
  fechaVencimiento: string;
  precioVenta: number;
  cantidadProductos: number;
  insumos: ItemDeInsumo[];
}

export interface ItemDeInsumo {
  cantidad: number;
  insumo: number;
}

@Injectable({
  providedIn: 'root'
})

export class ProductoService {
  private apiURL = `${environment.apiUrl}/productos`;
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' ,'Authorization':`Bearer ${localStorage.getItem('token')}`});
  constructor(private http: HttpClient) {
  }

  createProduct(idLote: string, prod: ProductoTerminadoRequest): Observable<ProductoTerminadoRequest> {
    return this.http.post<ProductoTerminadoRequest>(`${this.apiURL}/${idLote}`, prod, { headers: this.headers })
      .pipe(
        catchError(this.handleError)
      );
  }

  deleteProducto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiURL}/${id}`, { headers: this.headers })
      .pipe(
        catchError(this.handleError)
      );
  }

  /*agregarInsumos(productoId: string, insumos: ItemDeInsumo): Observable<any> {
    const url = `${this.apiURL}/${productoId}/agregarInsumos`; 
    console.log(url)
    return this.http.post(url, insumos, { headers: this.headers });
  }*/

  getProducto(productoId: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.apiURL}/${productoId}`, { headers: this.headers });
  }


  getProductos(): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.apiURL}/all`, { headers: this.headers });
  }

  private handleError(error: any) {
    console.error('An error occurred', error.message);
    return throwError(error);
  }

}
