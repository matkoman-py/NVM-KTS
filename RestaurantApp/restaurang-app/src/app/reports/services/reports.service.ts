import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ArticleProfit } from 'src/app/modules/shared/models/articleProfit';

@Injectable({
  providedIn: 'any',
})
export class ReportsService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  constructor(private http: HttpClient) {}

  articleProfitYearReport(year: number) : Observable<ArticleProfit>{
    return this.http.get<ArticleProfit>('/api/report/articleProfitYear/' + year);
  }

  articleProfitQuarter(year: number, quarter: number) : Observable<ArticleProfit>{
    return this.http.get<ArticleProfit>('/api/report/articleProfitQuarter/' + year + '/' + quarter);
  }

  articleProfitMonth(year: number, month: number) : Observable<ArticleProfit>{
    return this.http.get<ArticleProfit>('/api/report/articleProfitQuarter/' + year + '/' + month);
  }

  articleProfitDay(year: number, month: number, day: number) : Observable<ArticleProfit>{
    return this.http.get<ArticleProfit>('/api/report/articleProfitDay/' + year + '/' + month + '/' + day);
  }
}
