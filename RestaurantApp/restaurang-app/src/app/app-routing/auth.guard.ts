import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    // this will be passed from the route config
    // on the data property
    const expectedRole = route.data['expectedRoles'];
    // decode the token to get its payload
    const tokenPayload = this.findUserRole();

    if (tokenPayload === '') {
      this.router.navigate(['login']);
      return false;
    }
    if (!expectedRole.includes(tokenPayload)) {
      this.router.navigate(['home']);
      return false;
    }
    return true;
  }

  findUserRole = () => {
    let role = localStorage.getItem('token');
    console.log(role);
    let user: any;

    if (role) {
      user = jwt_decode(role);
    }
    console.log(user);
    if (user !== undefined)
      return user.authority[0].name === undefined
        ? user.authority[0].authority
        : user.authority[0].name;

    return '';
  };
}
