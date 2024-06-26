import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://your-api-url'; // Replace with your API URL

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, { username, password })
      .pipe(
        tap(response => {
          if (!response.mfaRequired) {
            localStorage.setItem('token', response.token);
          }
        }),
        catchError(this.handleError('login', []))
      );
  }

  verifyMfa(username: string, mfaCode: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/verify-mfa`, { username, mfaCode })
      .pipe(
        tap(response => localStorage.setItem('token', response.token)),
        catchError(this.handleError('verifyMfa', []))
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }
}
