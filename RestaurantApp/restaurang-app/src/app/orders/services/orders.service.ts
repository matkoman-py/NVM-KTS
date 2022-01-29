import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {
  ArticlesAndOrderDTO,
  Order,
  OrderStatus,
} from '../../modules/shared/models/order';

@Injectable({
  providedIn: 'any',
})
export class OrdersService {
  private header = new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: 'Bearer ' + localStorage.getItem('token'),
  });
  constructor(private http: HttpClient) {}

  createOrder(order: any): Observable<any> {
    return this.http.post<any>('/api/order', order, {
      headers: this.header,
    });
  }

  getOrders(): Observable<Order[]> {
    return this.http.get<Order[]>('/api/order/active', {
      headers: this.header,
    });
  }

  updateOrderStatus(id?: number, orderStatus?: String): Observable<Order[]> {
    return this.http.get<Order[]>(
      '/api/order/updateOrderStatus/' + id + '/' + orderStatus,
      {
        headers: this.header,
      }
    );
  }

  search(orderStatus: OrderStatus): Observable<Order[]> {
    let params = new HttpParams().set('orderStatus', orderStatus.value);
    return this.http.get<Order[]>(`/api/order/search`, {
      params: params,
      headers: this.header,
    });
  }

  addArticlesToOrder(
    articlesAndOrderDTO?: ArticlesAndOrderDTO
  ): Observable<Order> {
    return this.http.put<Order>(
      '/api/order/add-articles-to-order',
      articlesAndOrderDTO,
      {
        headers: this.header,
      }
    );
  }

  deleteArticleFromOrder(articleId?: number): Observable<Order> {
    return this.http.delete<Order>('/api/order/removeArticle/' + articleId, {
      headers: this.header,
    });
  }
}
