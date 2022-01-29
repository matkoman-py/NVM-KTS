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
  private header = new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: 'Bearer ' + localStorage.getItem('token'),
  });
  constructor(private http: HttpClient) {}

  getArticles(): Observable<Article[]> {
    return this.http.get<Article[]>('/api/article', { headers: this.header });
  }

  search(
    articleType: ArticleType,
    articleNameParam: string
  ): Observable<Article[]> {
    let params = new HttpParams()
      .set('type', articleType.value)
      .set('name', articleNameParam);
    return this.http.get<Article[]>(`/api/article/search`, {
      params: params,
      headers: this.header,
    });
  }

  delete(_id?: number): Observable<DeleteMessageDTO> {
    return this.http.delete<DeleteMessageDTO>(`/api/article/${_id}`, {
      headers: this.header,
    });
  }
}
