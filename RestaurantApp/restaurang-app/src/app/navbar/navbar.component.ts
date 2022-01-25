import { i18nMetaToJSDoc } from '@angular/compiler/src/render3/view/i18n/meta';
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
  role: string = '';

  findUserRole = () => {
    let role = localStorage.getItem('token');
    console.log(role);
    let user: any;

    if (role) {
      user = jwt_decode(role);
    }

    if (user !== undefined)
      return user.authority[0].name === undefined
        ? user.authority[0].authority
        : user.authority[0].name;
  };

  items: MenuItem[] = [
    {
      label: 'Login',
      icon: 'pi pi-fw pi-user',
      routerLink: '/login',
    },
  ];

  constructor(
    private ref: ChangeDetectorRef,
    private loginService: LoginService
  ) {}

  setNavbarItems = (role: string) => {
    this.role = role;
    if (this.role === '') {
      this.role = this.findUserRole();
    }
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
        visible: this.role === 'ROLE_MANAGER',
      },
      {
        label: 'Orders',
        icon: 'pi pi-fw pi-user',
        routerLink: '/active-orders',
        visible: this.role === 'BARMAN' || this.role === 'COOK',
      },
      {
        label: 'Reports',
        icon: 'pi pi-fw pi-user',
        routerLink: '/reports',
        visible: this.role === 'ROLE_MANAGER',
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
  };

  ngOnInit(): void {
    //NEOPHODNO RESETOVANJE ZBOG NAVIGIRANJA
    this.setNavbarItems('');
    this.loginService.getUserRole.subscribe((role) => {
      this.setNavbarItems(role);
    });
  }
}
