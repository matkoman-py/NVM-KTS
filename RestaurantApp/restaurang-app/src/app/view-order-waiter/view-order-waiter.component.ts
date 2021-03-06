import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { fabric } from 'fabric';
import { MessageService, PrimeNGConfig } from 'primeng/api';
import { ArticlesService } from '../articles/services/articles.service';
import {
  Article,
  ArticleForAdding,
  ArticleType,
} from '../modules/shared/models/article';
import { Order, OrderedArticleWithDTO } from '../modules/shared/models/order';
import {
  ArticleStatus,
  OrderedArticle,
} from '../modules/shared/models/orderedArticle';
import { OrdersService } from '../orders/services/orders.service';
import { TableLayoutService } from '../table-layout/services/table-layout.service';
import { ViewOrderService } from '../view-order/services/view-order.service';

@Component({
  selector: 'app-view-order-waiter',
  templateUrl: './view-order-waiter.component.html',
  styleUrls: ['./view-order-waiter.component.css'],
  providers: [MessageService],
})
export class ViewOrderWaiterComponent implements OnInit {
  order: Order = {};
  employeePin?: number;
  updateArticleStatusId?: number;
  articles: OrderedArticle[] = [];
  allArticles: Article[] = [];
  addedArticles: Article[] = [];
  orderId: number;
  quantity: number = 1;
  first = 0;
  rows = 5;
  articleForAdding: Article;
  displayConfirmDialog: boolean = false;
  displayFinishOrderDialog: boolean = false;
  displayArticleAddingDialog: boolean = false;
  displayQuantityDialog: boolean = false;
  displayAddNoteDialog: boolean = false;
  indexForAddingNote: number;
  note: string = '';

  statusDict = new Map<string, string>([
    ['NOT_TAKEN', 'Take article'],
    ['TAKEN', 'Start preparing'],
    ['PREPARING', 'Finish preparing'],
  ]);

  articleStatuses: ArticleStatus[] = [
    { name: 'DESSERT', value: 'DESSERT' },
    { name: 'DRINK', value: 'DRINK' },
    { name: 'APPETIZER', value: 'APPETIZER' },
    { name: 'MAIN_COURSE', value: 'MAIN_COURSE' },
    { name: 'ALL', value: '' },
  ];

  selectedArticleStatus: ArticleStatus = { name: '', value: '' };

  constructor(
    private articlesService: ArticlesService,
    private activatedRoute: ActivatedRoute,
    private viewOrderService: ViewOrderService,
    private orderService: OrdersService,
    private messageService: MessageService,
    private tableService: TableLayoutService,
    private primengConfig: PrimeNGConfig,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((paramsId) => {
      this.primengConfig.ripple = true;
      this.orderId = parseInt(paramsId['id']);
      this.getOrder(this.orderId);
      this.getArticlesForOrder(this.orderId);
    });
  }

  handleClose() {
    this.displayConfirmDialog = false;
    this.employeePin = undefined;
    this.updateArticleStatusId = undefined;
  }

  convertToDto(article: Article): OrderedArticleWithDTO {
    return {
      articleId: article.id,
      description: article.description ? article.description : '',
    };
  }

  addArticles() {
    var articlesAndOrderDto = {
      articles: this.addedArticles.map((article) => this.convertToDto(article)),
      orderId: this.orderId,
    };
    articlesAndOrderDto.articles.map((article) =>
      console.log(article.articleId)
    );
    this.orderService.addArticlesToOrder(articlesAndOrderDto).subscribe(() => {
      this.getOrder(this.orderId);
      this.getArticlesForOrder(this.orderId);
    });
    this.addedArticles = [];
    this.messageService.add({
      key: 'tc',
      severity: 'success',
      summary: 'Success!',
      detail: 'Successfully added articles to order!',
    });

    this.displayArticleAddingDialog = false;
  }

  openConfirmDialog() {
    this.displayConfirmDialog = true;
  }

  openQuantityDialog(article: Article) {
    this.articleForAdding = article;
    this.displayQuantityDialog = true;
  }

  removeFromArray(id: number) {
    this.addedArticles.splice(
      this.addedArticles.findIndex((e) => e.id === id),
      1
    );
  }

  openAddNoteDialogue(index: number) {
    this.indexForAddingNote = index;
    this.displayAddNoteDialog = true;
  }

  addNote() {
    this.addedArticles[this.indexForAddingNote].description = this.note;
    this.note = '';
    this.indexForAddingNote = -1;
    this.displayAddNoteDialog = false;
  }

  checkNote() {
    if (this.note.trim() === '') {
      return true;
    }
    return false;
  }

  addArticlesToArray() {
    for (var i = 0; i < this.quantity; i++) {
      this.addedArticles.push({ ...this.articleForAdding, description: '' });
    }
    this.displayQuantityDialog = false;
  }

  openModalForAddingArticles() {
    this.articlesService.getArticles().subscribe((data) => {
      this.allArticles = data;
    });
    this.displayArticleAddingDialog = true;
  }

  search() {
    this.articlesService
      .search(this.selectedArticleStatus, '')
      .subscribe((data) => {
        this.allArticles = data;
      });
  }

  openDialogFinishOrder() {
    this.displayFinishOrderDialog = true;
  }

  deleteArticleFromOrder(id: number) {
    this.orderService.deleteArticleFromOrder(id).subscribe(
      () => {
        this.getOrder(this.orderId);
        this.getArticlesForOrder(this.orderId);
        this.messageService.add({
          key: 'tc',
          severity: 'success',
          summary: 'Success!',
          detail: `Successfully deleted article with id: ${id} to order!`,
        });
      },
      (err) => {
        this.messageService.add({
          key: 'tc',
          severity: 'warn',
          summary: 'Fail',
          detail: err.error,
        });
        console.log(err.error);
      }
    );
  }

  finishOrder() {
    let canvas = new fabric.Canvas(null);

    this.tableService.getTableLayout().subscribe(res => {
      canvas.loadFromJSON(res, () => {
        canvas.renderAll();
        canvas.forEachObject((o : any) => {
          if(o.order_id === this.orderId) {
            o.getObjects("rect")[0].set('fill', 'gray');
            o.toObject = ((toObject) => {
              return (propertiesToInclude: any) => {
                return fabric.util.object.extend(toObject.call(o, propertiesToInclude), {
                  id: o.id,
                  order_id: ""
                });
              };
            })(o.toObject);
          } else {
            o.toObject = ((toObject) => {
              return (propertiesToInclude: any) => {
                return fabric.util.object.extend(toObject.call(o, propertiesToInclude), {
                  id: o.id,
                  order_id: o.order_id
                });
              };
            })(o.toObject);
          }
        });
        this.tableService.postTableLayout(JSON.stringify(canvas)).subscribe(() =>
        {
          this.displayConfirmDialog = false;
          this.displayFinishOrderDialog = false;
          this.router.navigate(['table-layout']);
          this.messageService.add({key: 'tc', severity:'success', summary: 'Order is finished', detail: 'Finished'});
        });
      })
    })
  }

  getOrder(id: number) {
    this.viewOrderService.getOrder(id).subscribe((data) => {
      this.order = data;
    });
  }

  getArticlesForOrder(id: number) {
    this.viewOrderService.getArticlesForOrder(id).subscribe((data) => {
      this.articles = data;
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
    return this.articles
      ? this.first === this.articles.length - this.rows
      : true;
  }

  isFirstPage(): boolean {
    return this.articles ? this.first === 0 : true;
  }

  checkQuantity(): boolean {
    if (this.quantity < 1) {
      return true;
    }
    return false;
  }
}
