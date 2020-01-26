import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthenticationService } from './authentication.service';
import { take, map } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(private authService: AuthenticationService, private router: Router) { }

  canActivate() {
    if(!this.authService.isTokenExpired()){
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }
  // canActivate(
  //   next: ActivatedRouteSnapshot,
  //   state: RouterStateSnapshot
  // ): Observable<boolean> {
  //   return this.authService.isLoggedIn
  //     .pipe(
  //       take(1),
  //       map((isLoggedIn: boolean) => {
  //         if (!isLoggedIn) {
  //           this.router.navigate(['/login']);
  //           return false;
  //         }
  //         return true;
  //       })
  //     )
  // }
}
