import { Component, OnInit } from '@angular/core';
import { LoginService } from './login/services/login.service';
import { LogoutService } from './logout/service/logout.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = 'restaurang-app';
  role: string | null = '';

  constructor(
    private loginService: LoginService,
    private logoutService: LogoutService
  ) {
    this.loginService.getUserRole.subscribe(() => {
      this.setRole(localStorage.getItem('role'));
    });
    this.logoutService.logout.subscribe(() => {
      this.setRole('');
    });
  }

  setRole = (role: string | null) => {
    this.role = role;
  };

  ngOnInit(): void {
    this.setRole(localStorage.getItem('role'));
  }
}
