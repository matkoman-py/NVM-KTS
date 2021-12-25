import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  items: MenuItem[] = [
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
    },
  ];
  constructor() {}

  ngOnInit(): void {}
}
