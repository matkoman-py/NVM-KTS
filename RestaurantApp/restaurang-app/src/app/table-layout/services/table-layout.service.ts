import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TableLayoutService {

  constructor(private http: HttpClient) { }
  
  getTableLayout(): Observable<String> {
    return this.http.get<String>('/api/table');
  }

  postTableLayout(canvas: String): Observable<String> {
    return this.http.post<String>('/api/table', canvas);
  }
}
