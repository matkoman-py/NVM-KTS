import { Component, OnInit } from '@angular/core';
import { LoginService } from '../services/login.service';
import { Login } from '../../modules/shared/models/login';
import { Router } from '@angular/router';
import jwt_decode from 'jwt-decode';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [MessageService],
})
export class LoginComponent implements OnInit {
  username: string;
  password: string;
  pincode: string;

  validLogin: boolean = true;
  validPin: boolean = true;

  constructor(private loginService: LoginService, private router: Router) {}

  onPriviledgeLogin = () => {
    const auth: Login = {
      username: this.username,
      password: this.password,
    };
    this.loginService.priviledgedUserLogin(auth).subscribe(
      (response: any) => {
        var role = this.findUserRole(response.body.accessToken);
        if (role != undefined) {
          localStorage.setItem('role', role);
          localStorage.setItem('isLoggedIn', 'true');
        }
        this.loginService.emitLogin();
        this.router.navigate(['reports']);

        console.log(jwt_decode(response.body.accessToken));
        //if((<any>jwt_decode(response.body.accessToken)).roles.filter((o: { name: string; }) => {o.name === 'ROLE_MANAGER'})) alert('jaj');
        // alert(this.findUserRole(jwt_decode(response.body.accessToken)));
      },
      (error) => {
        this.validLogin = false;
      }
    );
  };

  onEmployeeLogin = () => {
    const auth: Login = {
      username: 'employee',
      password: this.pincode,
    };
    this.loginService.priviledgedUserLogin(auth).subscribe(
      (response: any) => {
        console.log(jwt_decode(response.body.accessToken));
        var role = this.findUserRole(response.body.accessToken);
        if (role != undefined) {
          localStorage.setItem('role', role);
          localStorage.setItem('isLoggedIn', 'true');
        }
        this.loginService.emitLogin();
        if (role === 'WAITER') {
          this.router.navigate(['table-layout']);
        } else {
          this.router.navigate(['active-orders']);
        }
        // alert(this.findUserRole(jwt_decode(response.body.accessToken)));
      },
      (error) => {
        this.validPin = false;
      }
    );
  };

  findUserRole = (token: any) => {
    console.log('hahahah');
    let user: any;

    if (token) {
      user = jwt_decode(token);
    }

    if (user !== undefined) {
      return user.authority[0].name === undefined
        ? user.authority[0].authority
        : user.authority[0].name;
    }

    return undefined;
  };

  checkEmptyFields() {
    return (
      this.username === '' ||
      this.username === undefined ||
      this.password === '' ||
      this.password === undefined
    );
  }

  ngOnInit(): void {}
}
