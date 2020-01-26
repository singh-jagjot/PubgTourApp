import { Component, OnInit } from '@angular/core';
import { User } from '../../user';
import { AuthenticationService } from '../../authentication.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  hide: boolean = true;
  user: User = new User();
  error: string;
  constructor(private authService: AuthenticationService, private snackbar: MatSnackBar, private route: Router) { }

  ngOnInit() {
  }

  loginUser() {
    this.authService.loginUser(this.user).subscribe((data) => {
      if(data["token"]) {
        this.authService.setToken(data["token"]);
        this.snackbar.open("Welcome " + this.user.userName, "", {
          duration: 3000
        });
        this.route.navigate(["/home"]);
      }
    },
    error => {
      // console.log(error.error.message);
      this.error = error.status;
    });
    
  }
}
