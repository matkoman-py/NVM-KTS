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
export class UpdateArticleService {

  constructor(private http: HttpClient) { }

  getArticle(id : number): Observable<Article> {
    return this.http.get<Article>('/api/article/'+id);
  }
  getIngredients(): Observable<Ingredient[]> {
    return this.http.get<Ingredient[]>('/api/ingredient');
  }
  updateArticle(article: Article, id: number): Observable<Article> {
    return this.http.put<Article>('/api/article/'+id, article);
  }
}
