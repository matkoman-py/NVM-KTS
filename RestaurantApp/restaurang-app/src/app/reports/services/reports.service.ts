import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ArticleProfit } from 'src/app/modules/shared/models/articleProfit';

@Injectable({
  providedIn: 'any',
})
export class ReportsService {
  private header = new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: 'Bearer ' + localStorage.getItem('token'),
  });
  constructor(private http: HttpClient) {}

  articleProfitYearReport(year: number): Observable<ArticleProfit> {
    return this.http.get<ArticleProfit>(
      '/api/report/articleProfitYear/' + year,
      { headers: this.header }
    );
  }

  articleProfitQuarter(
    year: number,
    quarter: number
  ): Observable<ArticleProfit> {
    return this.http.get<ArticleProfit>(
      '/api/report/articleProfitQuarter/' + year + '/' + quarter,
      { headers: this.header }
    );
  }

  articleProfitMonth(year: number, month: number): Observable<ArticleProfit> {
    return this.http.get<ArticleProfit>(
      '/api/report/articleProfitMonth/' + year + '/' + month,
      { headers: this.header }
    );
  }

  articleProfitDay(
    year: number,
    month: number,
    day: number
  ): Observable<ArticleProfit> {
    return this.http.get<ArticleProfit>(
      '/api/report/articleProfitDay/' + year + '/' + month + '/' + day,
      { headers: this.header }
    );
  }

  articleProfitBetweenDates(
    dateFrom: Date,
    dateTo: Date
  ): Observable<ArticleProfit> {
    return this.http.get<ArticleProfit>(
      '/api/report/articleProfitBetweenDates/' +
        dateFrom.toUTCString() +
        '/' +
        dateTo.toUTCString(),
      { headers: this.header }
    );
  }
}
// "29-Sep-2021"
