import { Component, OnInit } from '@angular/core';
import { Message } from '@stomp/stompjs';
import { MessageService, PrimeNGConfig } from 'primeng/api';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
@Component({
  selector: 'app-notification-bar',
  templateUrl: './notification-bar.component.html',
  styleUrls: ['./notification-bar.component.css'],
  providers: [MessageService],
})
export class NotificationBarComponent implements OnInit {
  webSocketEndPoint: string = '/api/socket';
  stompClient: any;
  newOrdersSidebar = false;
  articleChangedSidebar = false;

  newOrderMessages: String[] = [];
  articleStatusChangedMessages: String[] = [];

  role: string | null = '';
  constructor(
    private messageService: MessageService,
    private primengConfig: PrimeNGConfig
  ) {}

  connect() {
    console.log('Initialize WebSocket Connection');
    let ws = new SockJS(this.webSocketEndPoint);
    this.stompClient = Stomp.over(ws);
    const _this = this;
    _this.stompClient.connect(
      {},
      function () {
        _this.stompClient.subscribe(
          '/orders/new-order',
          function (message: Message) {
            _this.onNewOrderReceived(message);
          }
        );
        _this.stompClient.subscribe(
          '/orders/article-status-changed',
          function (message: Message) {
            _this.onArticleStatusChangedReceived(message);
          }
        );
      },
      this.errorCallBack
    );
  }

  disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
    }
    console.log('Disconnected');
  }

  errorCallBack(error: string) {
    console.log('errorCallBack -> ' + error);
    setTimeout(() => {
      this.connect();
    }, 5000);
  }

  onNewOrderReceived(message: Message) {
    this.messageService.add({
      key: 'kitchen_workers',
      severity: 'info',
      summary: 'Notification',
      detail: 'New order: ' + message.body,
    });
    this.newOrderMessages.push(message.body);
  }

  onArticleStatusChangedReceived(message: Message) {
    this.messageService.add({
      key: 'waiters',
      severity: 'info',
      summary: 'Notification',
      detail: 'Article status changed: ' + message.body,
    });
    this.articleStatusChangedMessages.push(message.body);
  }

  openSidebar(): void {
    if (localStorage.getItem('role') === 'WAITER') {
      this.articleChangedSidebar = true;
      this.role = 'WAITER';
    } else {
      this.newOrdersSidebar = true;
      this.role = 'KITCHEN_WORKER';
    }
  }

  ngOnInit(): void {
    this.primengConfig.ripple = true;
    this.connect();
  }
}
