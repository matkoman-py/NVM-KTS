import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Order, OrderStatus } from '../../modules/shared/models/order';

@Injectable({
  providedIn: 'any',
})
export class OrdersService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  constructor(private http: HttpClient) {}

  getOrders(): Observable<Order[]> {
    return this.http.get<Order[]>('/api/order');
  }

  updateOrderStatus(id?: number, orderStatus?: String): Observable<Order[]>{
    return this.http.get<Order[]>('/api/order/updateOrderStatus/' + id + '/' + orderStatus);
  }

  search(
    orderStatus: OrderStatus,
  ): Observable<Order[]> {
    let params = new HttpParams()
      .set('orderStatus', orderStatus.value);
    return this.http.get<Order[]>(`/api/order/search`, { params: params });
  }
}
