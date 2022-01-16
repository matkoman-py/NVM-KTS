import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import {
  Article,
  ArticleType,
  DeleteMessageDTO,
} from 'src/app/modules/shared/models/article';
import {Ingredient} from 'src/app/modules/shared/models/ingredient';
import { Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class IngredientsService {

  constructor(private http: HttpClient) { }

  getIngredients(): Observable<Ingredient[]> {
    return this.http.get<Ingredient[]>('/api/ingredient');
  }

  deleteIngredients(id: number | undefined): Observable<string> {
    return this.http.delete('/api/ingredient/'+id, {responseType: 'text'});
  }
  createIngredient(ingredient: Ingredient): Observable<Ingredient> {
    return this.http.post<Ingredient>('/api/ingredient', ingredient);
  }
  updateIngredients(id: number, ingredient: Ingredient): Observable<Ingredient> {
    return this.http.put<Ingredient>('/api/ingredient/'+id, ingredient);
  }
}
