import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Article } from '../../modules/shared/models/article';
import { Order } from '../../modules/shared/models/order';
import { ArticleStatus, OrderedArticle } from '../../modules/shared/models/orderedArticle';

@Injectable({
  providedIn: 'any',
})
export class ViewOrderService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  constructor(private http: HttpClient) {}

  getOrder(id: number): Observable<Order> {
    return this.http.get<Order>('/api/order/' + id);
  }

  getArticlesForOrder(id: number): Observable<OrderedArticle[]> {
    return this.http.get<OrderedArticle[]>('/api/order/articles/' + id);
  }

  getArticle(id?: number): Observable<Article> {
    return this.http.get<Article>('/api/article/' + id);
  }

  changeStatus(orderedArticleId?: number, pin?: number): Observable<Article>{
    return this.http.put<Article>('/api/order/article/' + orderedArticleId + '/' + pin, null);
  }

  search(
    articleStatus: ArticleStatus,
    orderedArticleId?: number
  ): Observable<OrderedArticle[]> {
    let params = new HttpParams()
      .set('articleStatus', articleStatus.value);
    return this.http.get<OrderedArticle[]>(`/api/order/articles/search/` + orderedArticleId, { params: params });
  }
}
