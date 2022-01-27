import { Component, OnInit } from '@angular/core';
import { OrdersService } from './services/orders.service';
import { MessageService, PrimeNGConfig } from 'primeng/api';
import { Router } from '@angular/router';
import { Order, OrderStatus } from '../modules/shared/models/order';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css'],
  providers: [MessageService],
})
export class OrdersComponent implements OnInit {
  orders: Order[] = [];
  selectedOrder: Order = {};
  first = 0;
  rows = 5;

  statusDict = new Map<string, string>([
    ['ACTIVE', 'Active'],
    ['FINISHED', 'Finished'],
  ]);

  orderStatuses: OrderStatus[] = [
    { name: 'Not started', value: 'NOT_STARTED' },
    { name: 'Finished', value: 'FINISHED' },
  ];

  selectedOrderStatus: OrderStatus = { name: '', value: '' };

  constructor(
    private ordersService: OrdersService,
    private messageService: MessageService,
    private primengConfig: PrimeNGConfig,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.primengConfig.ripple = true;
    this.getOrders();
  }

  // search() {
  //   this.ordersService
  //     .search(this.selectedOrderStatus)
  //     .subscribe((data) => {
  //       this.orders = data;
  //     });
  // }

  getOrders() {
    this.ordersService.getOrders().subscribe((data) => {
      this.orders = data;
    });
  }

  seeOrder() {
    if (Object.keys(this.selectedOrder).length === 0) {
      this.messageService.add({
        key: 'tc',
        severity: 'error',
        summary: 'Error',
        detail: 'No order selected',
      });
      return;
    }
    this.router.navigate(['/view-order-kitchen/' + this.selectedOrder.id]);
  }

  next() {
    this.first = this.first + this.rows;
  }

  prev() {
    this.first = this.first - this.rows;
  }

  reset() {
    this.first = 0;
  }

  isLastPage(): boolean {
    return this.orders ? this.first === this.orders.length - this.rows : true;
  }

  isFirstPage(): boolean {
    return this.orders ? this.first === 0 : true;
  }
}
