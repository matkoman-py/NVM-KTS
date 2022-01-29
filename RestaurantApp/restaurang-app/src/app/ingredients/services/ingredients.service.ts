import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import {
  Article,
  ArticleType,
  DeleteMessageDTO,
} from 'src/app/modules/shared/models/article';
import { Ingredient } from 'src/app/modules/shared/models/ingredient';
import { Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class IngredientsService {
  private header = new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: 'Bearer ' + localStorage.getItem('token'),
  });
  constructor(private http: HttpClient) {}

  getIngredients(): Observable<Ingredient[]> {
    return this.http.get<Ingredient[]>('/api/ingredient', {
      headers: this.header,
    });
  }

  deleteIngredients(id: number | undefined): Observable<string> {
    return this.http.delete('/api/ingredient/' + id, {
      responseType: 'text',
      headers: this.header,
    });
  }
  createIngredient(ingredient: Ingredient): Observable<Ingredient> {
    return this.http.post<Ingredient>('/api/ingredient', ingredient, {
      headers: this.header,
    });
  }
  updateIngredients(
    id: number,
    ingredient: Ingredient
  ): Observable<Ingredient> {
    return this.http.put<Ingredient>('/api/ingredient/' + id, ingredient, {
      headers: this.header,
    });
  }
  searchIngredients(name: String, type: String): Observable<Ingredient[]> {
    return this.http.get<Ingredient[]>(
      '/api/ingredient/search?name=' + name + '&type=' + type,
      {
        headers: this.header,
      }
    );
  }
}
