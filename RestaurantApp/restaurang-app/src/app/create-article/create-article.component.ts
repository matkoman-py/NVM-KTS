import { Component, OnInit } from '@angular/core';
import {PrimeNGConfig, MessageService, Message} from 'primeng/api';
import { Article, ArticleType } from '../modules/shared/models/article';
import { Ingredient } from '../modules/shared/models/ingredient';
import { CreateArticleService } from './services/create-article.service';


import {Router} from "@angular/router"


@Component({
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.css'],
  providers: [MessageService]
})
export class CreateArticleComponent implements OnInit {

  article: Article = {name: "", makingPrice: 0, sellingPrice: 0, description: "", type: "", ingredients: []};
  nesto: string = "";
  selectedType: ArticleType = {name:"", value:""};

  articleTypes: ArticleType[] = [
    { name: 'DESSERT', value: 'DESSERT' },
    { name: 'DRINK', value: 'DRINK' },
    { name: 'APPETIZER', value: 'APPETIZER' },
    { name: 'MAIN_COURSE', value: 'MAIN_COURSE' },
  ];

  ingredients: Ingredient[] = [];
  selectedIngredients: Ingredient[] = [];
  constructor(
    private primengConfig: PrimeNGConfig,
    private createArticleService: CreateArticleService,
    private messageService: MessageService,
    private router: Router
  )
   {
  }

  validateAll(): boolean {
    if((this.article.name || '').toString().trim().length === 0 || (this.article.description || '').toString().trim().length === 0 ||  (this.article.type || '').toString().trim().length === 0 || this.article.makingPrice!  <= 0.0 || this.article.sellingPrice!  <= 0.0 || this.selectedIngredients.length == 0) {
      return false;
    }
    return true;
  }

  ngOnInit(): void {
    this.primengConfig.ripple = true;
    this.createArticleService.getIngredients().subscribe(res => this.ingredients = res);
  }


  

  setType(type: ArticleType) {
    this.article.type = type.name;
  }

  postArticle() {
    this.article.ingredients = this.selectedIngredients;
    this.createArticleService.postArticle(this.article).subscribe(data => {
    this.messageService.add({key: 'tc', severity:'success', summary: 'New article successfully created', detail: 'Created article'});
    this.selectedIngredients = [];
    this.article = {name: "", makingPrice: 0, sellingPrice: 0, description: "", type: "", ingredients: []};



    },
      error => console.log(error)
    );
  }

}
