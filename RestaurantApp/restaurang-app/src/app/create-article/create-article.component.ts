import { Component, OnInit } from '@angular/core';
import {PrimeNGConfig } from 'primeng/api';
import { Article, ArticleType } from '../modules/shared/models/article';
import { Ingredient } from '../modules/shared/models/ingredient';
import { CreateArticleService } from './services/create-article.service';


@Component({
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.css']
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

  ingredients: Ingredient[];

  selectedIngredients: Ingredient[] = [];
  constructor(
    private primengConfig: PrimeNGConfig,
    private createArticleService: CreateArticleService
  ) {
    this.ingredients = [
      {name: 'Paprika', isAllergen: false, id:1},
      {name: 'Prsuta', isAllergen: false, id:2},
      {name: 'Sir', isAllergen: false, id:3},
  ];
  }

  ngOnInit(): void {
    this.primengConfig.ripple = true;
  }
  onSave(): void {
    console.log(this.article);
    console.log(this.nesto);
  }
  setType(type: ArticleType) {
    console.log(type);
    this.article.type = type.name;
  }

  postArticle() {
    this.createArticleService.postArticle(this.article).subscribe((data) => {
      console.log(data);
    });
  }

}
