import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EditTableLayoutService {

  constructor(private http: HttpClient) { }

  postTableLayout(canvas: String): Observable<String> {
    return this.http.post<String>('/api/table', canvas);
  }
  
  getTableLayout(): Observable<String> {
    return this.http.get<String>('/api/table');
  }
}
