import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Article } from '../../modules/shared/models/article';
import { Order } from '../../modules/shared/models/order';
import {
  ArticleStatus,
  OrderedArticle,
} from '../../modules/shared/models/orderedArticle';

@Injectable({
  providedIn: 'any',
})
export class ViewOrderService {
  private header = new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: 'Bearer ' + localStorage.getItem('token'),
  });
  constructor(private http: HttpClient) {}

  getOrder(id: number): Observable<Order> {
    return this.http.get<Order>('/api/order/' + id, {
      headers: this.header,
    });
  }

  getArticlesForOrder(id: number): Observable<OrderedArticle[]> {
    return this.http.get<OrderedArticle[]>('/api/order/articles/' + id, {
      headers: this.header,
    });
  }

  getArticle(id?: number): Observable<Article> {
    return this.http.get<Article>('/api/article/' + id, {
      headers: this.header,
    });
  }

  changeStatus(orderedArticleId?: number, pin?: number): Observable<Article> {
    return this.http.put<Article>(
      '/api/order/article/' + orderedArticleId + '/' + pin,
      null,
      {
        headers: this.header,
      }
    );
  }

  search(
    articleStatus: ArticleStatus,
    orderedArticleId?: number
  ): Observable<OrderedArticle[]> {
    let params = new HttpParams().set('articleStatus', articleStatus.value);
    return this.http.get<OrderedArticle[]>(
      `/api/order/articles/search/` + orderedArticleId,
      { params: params, headers: this.header }
    );
  }
}
