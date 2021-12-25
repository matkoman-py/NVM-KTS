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
export class CreateArticleService {

  constructor(private http: HttpClient) { }
  postArticle(article: Article): Observable<Article> {
    return this.http.post<Article>('/api/article', article);
  }
}
