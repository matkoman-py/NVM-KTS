import { Component, OnInit } from '@angular/core';
import { IngredientsService } from './services/ingredients.service';
import { Article, ArticleType } from '../modules/shared/models/article';
import { MessageService, PrimeNGConfig } from 'primeng/api';
import { Ingredient } from '../modules/shared/models/ingredient';
import { Observable, Subject } from 'rxjs';


export class IngredientType {
  name: string;
};

@Component({
  selector: 'app-ingredients',
  templateUrl: './ingredients.component.html',
  styleUrls: ['./ingredients.component.css'],
  providers: [MessageService],
})
export class IngredientsComponent implements OnInit {

  ingredients: Ingredient[] = [];
  selectedIngredient: Ingredient = {id:0, name: '', allergen: false};
  ingredient: Ingredient = {};
  ingredientId: number = 0;
  value: boolean = true;
  nameInput: string = "";
  allergenInput: boolean = false;
  ingredientType: IngredientType[] = [
    { name: 'All'},
    { name: 'Just allergens'},
    { name: 'No allergens'},
  ];
  

  selectedType: IngredientType = {name: ''};
  constructor(private ingredientService: IngredientsService,
    private primengConfig: PrimeNGConfig,
    private messageService: MessageService
    ) { 
      }

  ngOnInit(): void {
    this.primengConfig.ripple = true;
    this.getAllIngredients();
  }

  getAllIngredients(): void {
    this.ingredientService.getIngredients().subscribe(res => {
      this.ingredients = res;
    })
  } 

  foo(e: Ingredient): void {
    this.ingredient.name = e.name;
    this.ingredient.allergen = e.allergen;
    this.ingredientId = e.id!;
  }

  changeState(value:any) : void {
    this.value = value;
  }

  isAnAllergen(): boolean {
    if(this.selectedIngredient.allergen) {
      return true;
    }
    return false;
  }

  create() : void {
    if (this.ingredients.filter(ing => ing.name == this.ingredient.name).length != 0) {
      this.messageService.add({
        key: 'tc',
        severity: 'warn',
        summary: 'Fail',
        detail: 'Ingredient with name ' + this.ingredient.name + ' already exists',
      });
      return;
  }
  this.ingredientService.createIngredient(this.ingredient).subscribe(res=> {
    this.messageService.add({
      key: 'tc',
      severity: 'success',
      summary: 'Success',
      detail: 'Ingredient successfully created',
    });
    this.getAllIngredients();
    this.selectedIngredient = {id:0};
  })
}

delete() : void {
  if (this.selectedIngredient.id == 0) {
    this.messageService.add({
      key: 'tc',
      severity: 'warn',
      summary: 'Fail',
      detail: 'No ingredient has been selected',
    });
    return;
  }
    this.ingredientService.deleteIngredients(this.selectedIngredient.id).subscribe(res=> {
      this.getAllIngredients();
      this.messageService.add({
        key: 'tc',
        severity: 'success',
        summary: 'Success',
        detail: res,
      });
      console.log(res);
      this.selectedIngredient = {id:0};
      
    })
  }

  update() : void {
    if (this.ingredients.filter(ing => ing.name == this.ingredient.name && ing.id != this.selectedIngredient.id).length != 0) {
      this.messageService.add({
        key: 'tc',
        severity: 'warn',
        summary: 'Fail',
        detail: 'Ingredient with name ' + this.ingredient.name + ' already exists',
      });
      return;
  }
  this.ingredient.name = this.ingredient.name == '' ? this.selectedIngredient.name : this.ingredient.name;
  this.ingredientService.updateIngredients(this.selectedIngredient.id!, this.ingredient).subscribe(res=> 
    {
      this.messageService.add({
        key: 'tc',
        severity: 'success',
        summary: 'Success',
        detail: 'Ingredient with id ' + this.selectedIngredient.id + ' successfully updated',
      });
      this.getAllIngredients();
      this.selectedIngredient = {id:0};
    
    })
}


}
