import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'any',
})
export class ReportsService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  constructor(private http: HttpClient) {}

  articleProfitYearReport(year: number) : Observable<void>{
    return this.http.get<void>('/api/report/articleProfitYear/' + year);
  }

}
