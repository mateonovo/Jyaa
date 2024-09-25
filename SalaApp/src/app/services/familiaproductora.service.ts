import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../environments/environment';

export interface FamiliaProductora {
  id: number;
  nombre: string;
  fechaInicio: string;
  zona: string;
}

@Injectable({
  providedIn: 'root'
})

export class FamiliaProductoraService {
  private apiURL = `${environment.apiUrl}/familias_productoras`;
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' ,'Authorization':`Bearer ${localStorage.getItem('token')}`});

  constructor(private http: HttpClient) {
  }

  
  createFamiliaProductora(fp: FamiliaProductora): Observable<FamiliaProductora> {
    return this.http.post<FamiliaProductora>(this.apiURL, fp, { headers: this.headers })
      .pipe(
        catchError(this.handleError)
      );
  }

  getFamiliaProductora(id: number): Observable<FamiliaProductora> {
    return this.http.get<FamiliaProductora>(`${this.apiURL}/${id}`, { headers: this.headers });
  }

  getFamiliasProductoras(): Observable<FamiliaProductora[]> {
    return this.http.get<FamiliaProductora[]>(`${this.apiURL}/all`,{ headers: this.headers });
  }


  updateFamiliaProductora(fp: FamiliaProductora): Observable<FamiliaProductora> {
    return this.http.put<FamiliaProductora>(this.apiURL, fp, { headers: this.headers })
      .pipe(
        catchError(this.handleError)
      );
  }

  deleteFamiliaProductora(id: number): Observable<void> {
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
