import { Component, OnInit } from '@angular/core';
import { LoginService } from '../services/login.service'
import { Login } from '../../modules/shared/models/login';
import { Router } from '@angular/router';
import jwt_decode from 'jwt-decode';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username : string;
  password: string;
  pincode: String;

  onPriviledgeLogin = () => {
    const auth: Login = {
      username: this.username,
      password: this.password
    }
    this.loginService.priviledgedUserLogin(auth).subscribe(
      (response: any) => {
        this.router.navigate(['home']);
        localStorage.setItem('token', response.body.accessToken);
        console.log(jwt_decode(response.body.accessToken));
        //if((<any>jwt_decode(response.body.accessToken)).roles.filter((o: { name: string; }) => {o.name === 'ROLE_MANAGER'})) alert('jaj');
        this.findUserRole();
      },
      (error) => {
        alert(error.status);
      });
  };

  onEmployeeLogin = () => {

  };

  findUserRole = () => {

  }

  constructor(private loginService : LoginService, private router: Router) { }

  ngOnInit(): void { }

}
