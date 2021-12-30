import { Component, OnInit } from '@angular/core';
import { LoginService } from '../services/login.service'
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

  username : string;
  password: string;
  pincode: string;

  validLogin: boolean = true;
  validPin: boolean = true;

  constructor(
    private loginService : LoginService, 
    private messageService: MessageService,
    private router: Router,
    ) { }

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
        this.findUserRole(jwt_decode(response.body.accessToken));
      },
      (error) => {
        this.validLogin = false;
      });
  };

  onEmployeeLogin = () => {
    this.loginService.employeeLogin(this.pincode).subscribe(
      (response: any) => {
        this.router.navigate(['home']);
      },
      (error) => {
        this.validPin = false;
      }
    )
  };

  findUserRole = (user: any) => {
    alert(user.roles[0].name);
  };

  checkEmptyFields() {
    return this.username === '' || this.username === undefined || this.password === '' || this.password === undefined;
  }

  ngOnInit(): void { }

}
