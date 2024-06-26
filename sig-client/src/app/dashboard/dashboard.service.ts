import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private apiUrl = 'http://your-api-url'; // Replace with your API URL

  constructor(private http: HttpClient) { }

  getStats(): Observable<any> {
    return this.http.get(`${this.apiUrl}/stats`);
  }

  getChartData(): Observable<any> {
    return this.http.get(`${this.apiUrl}/chart-data`);
  }

  getTasks(): Observable<any> {
    return this.http.get(`${this.apiUrl}/tasks`);
  }

  getCases(): Observable<any> {
    return this.http.get(`${this.apiUrl}/cases`);
  }
}
