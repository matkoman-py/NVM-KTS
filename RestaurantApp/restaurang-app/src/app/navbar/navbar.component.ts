import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import jwt_decode from 'jwt-decode';
import { MenuItem } from 'primeng/api';
import { LoginService } from '../login/services/login.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  role : string = '';

  findUserRole = () => {
    let role = localStorage.getItem('token');
    console.log(role);
    let user : any;

    if(role) {
      user = jwt_decode(role);
    }

    if(user !== undefined)
      return user.authority[0].name === undefined ? user.authority[0].authority : user.authority[0].name;
  }

  items: MenuItem[] = [
    {
      label: 'Login',
      icon: 'pi pi-fw pi-user',
      routerLink: '/login',
    },
  ];

  constructor(
    private ref : ChangeDetectorRef,
    private loginService : LoginService) {
  }

  setNavbarItems = () => {
    this.loginService.getUserRole.subscribe(role => {
      this.role = role;
      this.items = [
        {
          label: 'Home',
          icon: 'pi pi-fw pi-home',
          routerLink: '/home',
    
        },
        {
          label: 'Login',
          icon: 'pi pi-fw pi-user',
          routerLink: '/login',
        },
        {
          label: 'Articles',
          icon: 'pi pi-fw pi-user',
          routerLink: '/articles',
        },
        {
          label: 'Employees',
          icon: 'pi pi-fw pi-user',
          routerLink: '/employees',
          visible: this.role === 'ROLE_MANAGER',
        },
        {
          label: 'Create Article',
          icon: 'pi pi-fw pi-plus-circle',
          routerLink: '/create-article',
          visible: this.role === 'ROLE_MANAGER',
        },
      ];
    });
  }

  ngOnInit(): void {
    this.setNavbarItems();
  }

  
}
