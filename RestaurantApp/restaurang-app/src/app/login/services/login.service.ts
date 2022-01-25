import { Injectable, Output } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Login } from '../../modules/shared/models/login';
import { Token } from '../../modules/shared/models/token';
import { Observable } from 'rxjs';
import jwt_decode from 'jwt-decode';
import { EventEmitter } from '@angular/core';

@Injectable({
  providedIn: 'any',
})
export class LoginService {
  @Output() getUserRole: EventEmitter<any> = new EventEmitter();

  priviledgedUserLogin(auth: Login): Observable<any> {
    return this.http.post<Token>('/api/auth/login', auth, {
      responseType: 'json',
      observe: 'response',
    });
  }

  employeeLogin(pincode: string): Observable<any> {
    return this.http.get<any>('/api/auth/login/' + pincode, {
      responseType: 'json',
      observe: 'response',
    });
  }

  emitLogin = () => {
    this.getUserRole.emit();
  };

  constructor(private http: HttpClient) {}
}
