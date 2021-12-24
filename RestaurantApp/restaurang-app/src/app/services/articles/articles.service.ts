import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {
  Article,
  ArticlesDTO,
  ArticleType,
} from 'src/app/articles/article.model';
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

    // return [
    //   {
    //     _id: 1,
    //     name: 'prvi',
    //     makingPrice: 600,
    //     sellingPrice: 700,
    //     description: 'PUCA',
    //     type: 'Dessert',
    //     ingredients: [],
    //   },
    //   {
    //     _id: 2,
    //     name: 'drugi',
    //     makingPrice: 600,
    //     sellingPrice: 700,
    //     description: 'PUCA',
    //     type: 'Dessert',
    //     ingredients: [],
    //   },
    //   {
    //     _id: 3,
    //     name: 'treci',
    //     makingPrice: 600,
    //     sellingPrice: 700,
    //     description: 'PUCA',
    //     type: 'Dessert',
    //     ingredients: [],
    //   },
    // ];
  }

  search(articleType: ArticleType, articleNameParam: string): void {
    console.log('EVO MEEE AAA');
    // this.http
    //   .post<Article[]>('http://localhost:9090/api/article')
    //   .subscribe((res) => {
    //     console.log(res);
    //   });
  }

  delete(_id?: number): void {
    console.log('EVO MEEE AAA');
    // this.http
    //   .delete<Article[]>('http://localhost:9090/api/article')
    //   .subscribe((res) => {
    //     console.log(res);
    //   });
  }
}
