import { Component, OnInit } from '@angular/core';
import { ViewOrderService } from './services/view-order.service';
import { MessageService, PrimeNGConfig } from 'primeng/api';
import {ActivatedRoute, Router} from "@angular/router"
import { Order } from '../modules/shared/models/order';
import { OrderedArticle } from '../modules/shared/models/orderedArticle';

@Component({
  selector: 'app-view-order',
  templateUrl: './view-order.component.html',
  styleUrls: ['./view-order.component.css'],
  providers: [MessageService],
})
export class ViewOrderComponent implements OnInit {
  order: Order = {};
  employeePin?: number;
  updateArticleStatusId?: number;
  articles: OrderedArticle[] = []
  orderId: number;
  first = 0;
  rows = 5;
  display: boolean = false;

  statusDict = new Map<string, string>([
    ["NOT_TAKEN", "Take article"],
    ["TAKEN", "Start preparing"],
    ["PREPARING", "Finish preparing"]
    ]);

  constructor(
    private activatedRoute: ActivatedRoute,
    private viewOrderService: ViewOrderService,
    private messageService: MessageService,
    private primengConfig: PrimeNGConfig,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(paramsId => {
        this.primengConfig.ripple = true;
        this.orderId = parseInt(paramsId['id']);
        this.getOrder(this.orderId);
        this.getArticlesForOrder(this.orderId);
    });
  }

  handleClose(){
    this.display = false;
    this.employeePin = undefined;
    this.updateArticleStatusId = undefined;
  }

  openConfirmDialog(articleId?: number){
    this.display = true;
    this.updateArticleStatusId = articleId;
  }

  updateStatus(){
    this.viewOrderService.changeStatus(this.updateArticleStatusId, this.employeePin).subscribe((data) => {
        this.getArticlesForOrder(this.orderId);
        this.messageService.add({
            key: 'tc',
            severity: 'success',
            summary: 'Success!',
            detail: "Status of article successfully updaed",
            });
        this.handleClose();
    },err => {
        this.messageService.add({
            key: 'tc',
            severity: 'warn',
            summary: 'Fail',
            detail: err.error,
            });
        console.log(err.error);
        this.handleClose();
      });
  }

  getOrder(id: number) {
    this.viewOrderService.getOrder(id).subscribe((data) => {
      this.order = data;
    });
  }

  getArticlesForOrder(id: number) {
    this.viewOrderService.getArticlesForOrder(id).subscribe((data) => {
      this.articles = data;

      for(let article of this.articles){
        this.viewOrderService.getArticle(article.articleId).subscribe((result) => {
            article.articleName = result.name;
            article.articleDescription = result.description;
        });
    }

    });
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
    return this.articles ?
      this.first === this.articles.length - this.rows :
      true;
  }

  isFirstPage(): boolean {
    return this.articles ? this.first === 0 : true;
  }
}
