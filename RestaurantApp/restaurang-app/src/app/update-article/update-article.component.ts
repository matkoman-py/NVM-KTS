import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UpdateArticleService } from './services/update-article.service';
import { Article, ArticleType } from '../modules/shared/models/article';
import {PrimeNGConfig, MessageService, Message} from 'primeng/api';
import { Ingredient } from '../modules/shared/models/ingredient';
import {Router} from "@angular/router"
import { _resolveDirectionality } from '@angular/cdk/bidi/directionality';


@Component({
  selector: 'app-update-article',
  templateUrl: './update-article.component.html',
  styleUrls: ['./update-article.component.css'],
  providers: [MessageService]

})
export class UpdateArticleComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute, private updateArticleService: UpdateArticleService, private messageService: MessageService, private router: Router) { }
  article: Article = {name: "", makingPrice: 0, sellingPrice: 0, description: "", type: "", ingredients: []};
  selectedType: ArticleType = {name:"", value:""};
  id: number = 0;

  articleTypes: ArticleType[] = [
    { name: 'DESSERT', value: 'DESSERT' },
    { name: 'DRINK', value: 'DRINK' },
    { name: 'APPETIZER', value: 'APPETIZER' },
    { name: 'MAIN_COURSE', value: 'MAIN_COURSE' },
  ];

  ingredients: Ingredient[] = [];
  selectedIngredients: Ingredient[] = [];

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(paramsId => {
      this.id = parseInt(paramsId['id']);
      this.updateArticleService.getArticle(parseInt(paramsId['id'])).subscribe(res => {
        this.article = res;
        switch(res.type) {
          case "DESSERT":
            this.selectedType = this.articleTypes[0];
            break;
          case "DRINK":
            this.selectedType = this.articleTypes[1];
            break;
          case "APPETIZER":
            this.selectedType = this.articleTypes[2];
            break;
          case "MAIN_COURSE":
            this.selectedType = this.articleTypes[3];
            break;
        } 
        this.updateArticleService.getIngredients().subscribe(data => {this.ingredients = data});
        this.selectedIngredients = res.ingredients!;
      });
  });
  }

  setType(type: ArticleType) {
    console.log(this.selectedIngredients);
    console.log(type);
    this.article.type = type.name;
  }

  putArticle(): void {
    this.article.ingredients = this.selectedIngredients;
    let that = this;
    this.updateArticleService.updateArticle(this.article, this.id).subscribe(res => {
      this.messageService.add({key: 'tc', severity:'success', summary: 'Article successfully updated', detail: 'Updated article'});
      setTimeout(function() {
        that.router.navigate(["/articles"]);
      }, 1000);
    })
  }

  validateAll(): boolean {
    if((this.article.name || '').toString().trim().length === 0 || (this.article.description || '').toString().trim().length === 0 ||  (this.article.type || '').toString().trim().length === 0 || this.article.makingPrice!  <= 0.0 || this.article.sellingPrice!  <= 0.0 || this.selectedIngredients.length == 0) {
      return false;
    }
    return true;
  }

}
