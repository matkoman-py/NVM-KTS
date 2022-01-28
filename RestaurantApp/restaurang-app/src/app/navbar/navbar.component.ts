import { i18nMetaToJSDoc } from '@angular/compiler/src/render3/view/i18n/meta';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import jwt_decode from 'jwt-decode';
import { MenuItem } from 'primeng/api';
import { LoginService } from '../login/services/login.service';
import { LogoutService } from '../logout/service/logout.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  role: string | null = '';

  items: MenuItem[] = [
    {
      label: 'Login',
      icon: 'pi pi-fw pi-sign-in',
      routerLink: '/login',
    },
  ];

  constructor(
    private loginService: LoginService,
    private logoutService: LogoutService
  ) {
    this.loginService.getUserRole.subscribe(() => {
      this.setNavbarItems();
    });
    this.logoutService.logout.subscribe(() => {
      this.setLogoutItems();
    });
  }

  setLogoutItems = () => {
    this.items = [
      {
        label: 'Login',
        icon: 'pi pi-fw pi-sign-in',
        routerLink: '/login',
      },
    ];
  };

  setNavbarItems = () => {
    this.role = localStorage.getItem('role');
    if (this.role === '') {
      this.items = [
        {
          label: 'Login',
          icon: 'pi pi-fw pi-sign-in',
          routerLink: '/login',
        },
      ];
    } else {
      this.items = [
        {
          label: 'Home',
          icon: 'pi pi-fw pi-home',
          routerLink: '/home',
          visible: this.role === 'ROLE_MANAGER' || this.role === 'WAITER',
        },
        {
          label: 'Articles',
          icon: 'pi pi-fw pi-shopping-bag',
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
          icon: 'pi pi-fw pi-chart-pie',
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
        {
          label: 'Ingredients',
          icon: 'pi pi-fw pi-list',
          routerLink: '/ingredients',
          visible: this.role === 'ROLE_MANAGER',
        },
        {
          label: 'Tables',
          icon: 'pi pi-fw pi-microsoft',
          routerLink: '/table-layout',
          visible: this.role === 'WAITER'
        },
        {
          label: 'Edit tables',
          icon: 'pi pi-fw pi-microsoft',
          routerLink: '/edit-table-layout',
          visible: this.role === 'ROLE_MANAGER'
        },
        {
          label: 'Logout',
          icon: 'pi pi-fw pi-sign-out',
          routerLink: '/logout',
          visible:
            this.role === 'ROLE_MANAGER' ||
            this.role === 'COOK' ||
            this.role === 'WAITER' ||
            this.role === 'BARMAN',
        },
        
      ];
    }
  };

  ngOnInit(): void {
    this.setNavbarItems();
  }
}
