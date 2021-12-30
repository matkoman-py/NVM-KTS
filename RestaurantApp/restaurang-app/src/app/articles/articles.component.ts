import { Component, OnInit } from '@angular/core';
import { ArticlesService } from './services/articles.service';
import { Article, ArticleType } from '../modules/shared/models/article';
import { MessageService, PrimeNGConfig } from 'primeng/api';
import {Router} from "@angular/router"


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
    { name: 'DESSERT', value: 'DESSERT' },
    { name: 'DRINK', value: 'DRINK' },
    { name: 'APPETIZER', value: 'APPETIZER' },
    { name: 'MAIN_COURSE', value: 'MAIN_COURSE' },
  ];
  selectedArticleType: ArticleType = { name: '', value: '' };
  nameSearchParam = '';
  first = 0;
  rows = 10;

  constructor(
    private articlesService: ArticlesService,
    private messageService: MessageService,
    private primengConfig: PrimeNGConfig,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.primengConfig.ripple = true;
    this.getArticles();
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

  getArticles() {
    this.articlesService.getArticles().subscribe((data) => {
      this.articles = data;
    });
  }

  search() {
    this.articlesService
      .search(this.selectedArticleType, this.nameSearchParam)
      .subscribe((data) => {
        this.articles = data;
      });
  }

  create() {
    //redirect na markuzinu
    this.router.navigate(['/create-article'])
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
    this.router.navigate(['/update-article/'+this.selectedArticle.id]);
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
    this.articlesService.delete(this.selectedArticle.id).subscribe((res) => {
      this.messageService.add({
        key: 'tc',
        severity: 'success',
        summary: 'Success',
        detail: res.message,
      });
      this.selectedArticle = {};
      this.getArticles();
    });
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
