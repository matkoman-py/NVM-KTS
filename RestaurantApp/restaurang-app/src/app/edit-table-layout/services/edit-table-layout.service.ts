import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class EditTableLayoutService {
  private header = new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: 'Bearer ' + localStorage.getItem('token'),
  });
  constructor(private http: HttpClient) {}

  postTableLayout(canvas: String): Observable<String> {
    return this.http.post<String>('/api/table', canvas, {
      headers: this.header,
    });
  }

  getTableLayout(): Observable<String> {
    return this.http.get<String>('/api/table', {
      headers: this.header,
    });
  }
}
