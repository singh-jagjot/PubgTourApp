import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from './modules/authentication/authentication.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  
  title = 'PubGUI';
  isLoggedIn$: Observable<boolean>; 
  constructor(private authService: AuthenticationService) {}

  ngOnInit(): void {
    this.isLoggedIn$ = this.authService.isLoggedIn;
    // this.isLoggedIn$ = this.authService.isLogout();
  }

  logout() {
    this.authService.removeToken();
  }
}
