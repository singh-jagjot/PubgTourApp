import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as jwt_token from 'jwt-decode';
import { Observable, BehaviorSubject } from 'rxjs';

export const TOKEN_NAME: string = "jwt_token";
@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private jj = new Observable<boolean>((observer) => {
    observer.next(!this.isTokenExpired());
  });
  private loggedIn = new BehaviorSubject<boolean>(false); // {1}

  get isLoggedIn() {
    return this.loggedIn.asObservable(); // {2}
  }

  backendServerAuthenticationEndpoint: string = "http://localhost:8082/api/user";
  constructor(private http: HttpClient) {
  }

  registerNewUser(user) {
    return this.http.post(this.backendServerAuthenticationEndpoint + "/register", user, { responseType: 'text' });
  }

  loginUser(user) {
    return this.http.post(this.backendServerAuthenticationEndpoint + "/login", user);
  }

  setToken(token) {
    this.loggedIn.next(true);
    return localStorage.setItem(TOKEN_NAME, token);
  }
  getToken() {
    if (localStorage.getItem(TOKEN_NAME)) this.loggedIn.next(true);
    return localStorage.getItem(TOKEN_NAME);
  }
  removeToken() {
    this.loggedIn.next(false);
    return localStorage.removeItem(TOKEN_NAME);
  }

  isLogout() {
    // return new Observable<boolean>((observer) => {
    //   observer.next(this.isTokenExpired());
    // });
    return this.jj;
  }
  getTokenExpirationDate(token): Date {
    const decoded = jwt_token(token);
    if (decoded.exp === undefined) return null;
    const date = new Date();
    date.setUTCSeconds(decoded.exp);
    return date;
  }

  isTokenExpired(token?: string): boolean {
    if (!token) token = this.getToken();
    if (!token) return true;
    const date = this.getTokenExpirationDate(token);
    if (date === undefined || date === null) return false;
    return !(date.valueOf() > new Date().valueOf());
  }

}
