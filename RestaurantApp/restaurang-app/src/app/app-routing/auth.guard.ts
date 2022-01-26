import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';

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
    const tokenPayload = localStorage.getItem('role');

    if (localStorage.getItem('isLoggedIn') !== 'true') {
      this.router.navigate(['login']);
      return false;
    }
    if (!expectedRole.includes(tokenPayload)) {
      if (tokenPayload === 'WAITER' || tokenPayload === 'ROLE_MANAGER') {
        this.router.navigate(['home']);
      } else {
        this.router.navigate(['active-orders']);
      }
      return false;
    }
    return true;
  }
}
