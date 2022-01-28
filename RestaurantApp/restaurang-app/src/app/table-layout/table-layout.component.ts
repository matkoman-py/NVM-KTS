import { FormatWidth, getLocaleDateTimeFormat } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ArcElement } from 'chart.js';
import { fabric } from 'fabric';
import { ArticlesService } from '../articles/services/articles.service';
import { EmployeesService } from '../employees/services/employees.service';
import { Article } from '../modules/shared/models/article';
import { Order } from '../modules/shared/models/order';
import { ArticleStatus, OrderedArticle } from '../modules/shared/models/orderedArticle';
import { OrdersService } from '../orders/services/orders.service';
import { ViewOrderService } from '../view-order/services/view-order.service';
import { TableLayoutService } from './services/table-layout.service';
import { OrderCreation } from '../modules/shared/models/order_creation'

@Component({
  selector: 'app-table-layout',
  templateUrl: './table-layout.component.html',
  styleUrls: ['./table-layout.component.css']
})
export class TableLayoutComponent implements OnInit {

  private canvas = new fabric.Canvas('demoCanvas');
  private json = "";

  selectedTableId: number = 0;

  order: Order = {};
  employeePin? : number;
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
    private tableLayoutService: TableLayoutService,
    private router: Router,
    private orderService: OrdersService,
    private articlesService: ArticlesService,
    private viewOrderService: ViewOrderService,
    private employeeService: EmployeesService
    ) { }

  addArticles() {
    // var articlesAndOrderDto = {
    //   articleIds: this.addedArticles.map((article) =>
    //     article.id ? article.id : 0
    //   ),
    //   orderId: this.orderId,
    // };

    this.displayConfirmDialog = true;
  }

  handleClose() {
    this.displayConfirmDialog = false;
    this.employeePin = undefined;
  }

  checkPin() {

    // var date: string = new Date().toLocaleString('en-US', datetimeOptions);
    var date: string = this.parseDate(new Date());

    this.employeeService.getEmployeeIdByPincode(this.employeePin).subscribe(() =>
    {
      var order: OrderCreation = {
        articles: this.addedArticles.map((article) => 
          article.id ? article.id : 0
          ),
        orderDate: date,
        deleted: false,
        tableNumber: this.selectedTableId,
        description: "mnogo dobro",
        employeePin: this.employeePin
      }


      this.orderService.createOrder(order).subscribe(res => {
        this.canvas.forEachObject((o : any) => {

          var table = o.getObjects('rect')[0];
          console.log(this.selectedTableId)
          if(o.id === this.selectedTableId && this.selectedTableId !== undefined) {
            o.order_id = res.id;
            table.set('fill', 'green');

            o.toObject = ((toObject) => {
              return (propertiesToInclude: any) => {
                return fabric.util.object.extend(toObject.call(o, propertiesToInclude), {
                  id: o.id,
                  order_id: res.id
                });
              };
            })(o.toObject);
          }
          else {
            o.toObject = ((toObject) => {
              return (propertiesToInclude: any) => {
                return fabric.util.object.extend(toObject.call(o, propertiesToInclude), {
                  id: o.id,
                  order_id: o.order_id
                });
              };
            })(o.toObject);
          }
          this.canvas.renderAll();
          
        })
        console.log(JSON.stringify(this.canvas))
        this.tableLayoutService.postTableLayout(JSON.stringify(this.canvas)).subscribe()
      });
  
      this.displayArticleAddingDialog = false;
      this.addedArticles = [];
      this.displayConfirmDialog = false;
    })
  }

  parseDate(date: Date) {
    var dateString: string = date.getFullYear() + "-" + ("0" + (date.getMonth() + 1)).slice(-2) + "-" + ("0" + (date.getDate())).slice(-2) + 'T' + 
    ("0" + (date.getHours() + 1)).slice(-2) + ":" + ("0" + (date.getMinutes() + 1)).slice(-2) + ":" + ("0" + (date.getSeconds() + 1)).slice(-2) + '.' + date.getMilliseconds();
    return dateString;
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

  openModalForAddingArticles() {
    this.articlesService.getArticles().subscribe((data) => {
      this.allArticles = data;
    });
    this.displayArticleAddingDialog = true;
  }

  addArticlesToArray() {
    for (var i = 0; i < this.quantity; i++) {
      this.addedArticles.push(this.articleForAdding);
    }
    this.displayQuantityDialog = false;
  }

  search() {
    this.articlesService
      .search(this.selectedArticleStatus, '')
      .subscribe((data) => {
        this.allArticles = data;
      });
  }

  getArticlesForOrder(id: number) {
    this.viewOrderService.getArticlesForOrder(id).subscribe((data) => {
      this.articles = data;
      this.getArticleNamesAndDescriptions();
    });
  }

  checkQuantity(): boolean {
    if (this.quantity < 1) {
      return true;
    }
    return false;
  }

  getArticleNamesAndDescriptions() {
    for (let article of this.articles) {
      this.viewOrderService
        .getArticle(article.articleId)
        .subscribe((result) => {
          article.articleName = result.name;
          article.articleDescription = result.description;
          article.price = result.sellingPrice;
          article.image = result.image;
          article.type = result.type;
        });
    }
  }

  ngOnInit(): void {
    this.canvas = new fabric.Canvas('demoCanvas');
    this.deserialize();
  }

  deserialize() {
    this.tableLayoutService.getTableLayout()
        .subscribe((data: any) => {
          console.log(data);    

          this.canvas.loadFromJSON(data, () => {
            this.canvas.renderAll();
            this.setTableEvents();
          })
  });
  }

  setTableEvents() {
    this.canvas.forEachObject((o : any) => {
      var table = o.getObjects('rect')[0];

      o.selectable = false;
      if(o.order_id !== "")
        table.set('fill', 'green');
      
      o.on('mousedown', (e : any) => {
        if(o.order_id !== "") {
          this.router.navigate(['view-order-waiter/' + o.order_id])
        }
        else {
          this.selectedTableId = o.id;
          this.openModalForAddingArticles();
        }
      })
    })
    this.canvas.renderAll();
  }

  checkIfTablesHaveOrders() {
    this.canvas.forEachObject((o : any) => {
      var table = o.getObjects('rect')[0];

      if(o.order_id !== undefined)
        table.set('fill', 'green');
    })
  }


}
