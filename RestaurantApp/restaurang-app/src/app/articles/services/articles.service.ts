import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {
  Article,
  ArticlesDTO,
  ArticleType,
  DeleteMessageDTO,
} from 'src/app/modules/shared/models/article';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
@Injectable({
  providedIn: 'any',
})
export class ArticlesService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  constructor(private http: HttpClient) {}

  getArticles(): Observable<Article[]> {
    return this.http.get<Article[]>('/api/article');
  }

  search(
    articleType: ArticleType,
    articleNameParam: string
  ): Observable<Article[]> {
    let params = new HttpParams()
      .set('type', articleType.value)
      .set('name', articleNameParam);
    return this.http.get<Article[]>(`api/article/search`, { params: params });
  }

  delete(_id?: number): Observable<DeleteMessageDTO> {
    return this.http.delete<DeleteMessageDTO>(`/api/article/${_id}`);
  }
}
