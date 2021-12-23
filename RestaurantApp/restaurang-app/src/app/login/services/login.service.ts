import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Login } from '../../modules/shared/models/login';
import { Token } from '../../modules/shared/models/token';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'any'
})
export class LoginService {
  private headers = new HttpHeaders({ "Content-Type": "application/json" });
  

  priviledgedUserLogin(auth: Login): Observable<any> {
    return this.http.post<Token>("/api/auth/login", auth, { responseType: 'json', observe:'response'});
  }

  constructor(private http: HttpClient) { }
}
