import { Component, OnInit } from '@angular/core';
import { ArticlesService } from '../services/articles/articles.service';
import { Article, ArticleType } from './article.model';
import { MessageService, PrimeNGConfig } from 'primeng/api';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.css'],
  providers: [MessageService],
})
export class ArticlesComponent implements OnInit {
  articles: Article[] = [];
  selectedArticle: Article = {};
  articleTypes: ArticleType[] = [
    { name: 'Dessert', value: 'DESSERT' },
    { name: 'Drink', value: 'DRINK' },
  ];
  selectedArticleType: ArticleType = {};
  nameSearchParam = '';
  first = 0;
  rows = 10;

  constructor(
    private articlesService: ArticlesService,
    private messageService: MessageService,
    private primengConfig: PrimeNGConfig
  ) {}

  ngOnInit(): void {
    this.primengConfig.ripple = true;
    this.getArticles();
  }

  getArticles() {
    this.articlesService.getArticles().subscribe((data) => {
      this.articles = data;
    });
    // this.articles = this.articlesService.getArticles();
    console.log(this.articlesService.getArticles());
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

  search() {
    this.articlesService.search(this.selectedArticleType, this.nameSearchParam);
  }

  create() {
    //redirect na markuzinu
  }

  update() {
    if (Object.keys(this.selectedArticle).length === 0) {
      this.messageService.add({
        key: 'tc',
        severity: 'error',
        summary: 'Error',
        detail: 'No article selected',
      });
      return;
    }
    //redirect na markuzinu
  }

  delete() {
    if (Object.keys(this.selectedArticle).length === 0) {
      this.messageService.add({
        key: 'tc',
        severity: 'error',
        summary: 'Error',
        detail: 'No article selected',
      });
      return;
    }
    this.articlesService.delete(this.selectedArticle._id);
  }

  isLastPage(): boolean {
    return this.articles
      ? this.first === this.articles.length - this.rows
      : true;
  }

  isFirstPage(): boolean {
    return this.articles ? this.first === 0 : true;
  }
}
