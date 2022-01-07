import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Article } from '../../modules/shared/models/article';
import { Order } from '../../modules/shared/models/order';
import { OrderedArticle } from '../../modules/shared/models/orderedArticle';

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
}
