import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../environments/environment';

export interface Usuario {
    email: string;
    password: string;
  }

@Injectable({
    providedIn: 'root'
  })
  
  export class LoginService {
    private apiURL = `${environment.apiUrl}/login`;
  
    constructor(private http: HttpClient) {
    }

    login(user: Usuario): Observable<Usuario> {
        const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
        return this.http.post<Usuario>(this.apiURL, user, { headers: headers })
          .pipe(
            catchError(this.handleError)
          );
      }

      IsLogin(): boolean {
        if (typeof localStorage !== 'undefined' && localStorage.getItem('token')) {
          return true;
        } else {
          return false;
        }
      }
      

    private handleError(error: any) {
      console.error(error);
      return throwError(error);
    }
}