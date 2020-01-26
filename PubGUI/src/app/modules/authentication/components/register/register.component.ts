import { Component, OnInit } from '@angular/core';
import { User } from '../../user';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../authentication.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  hide: boolean = true;
  newUser: User = new User();
  error: string;
  constructor(private authService: AuthenticationService, private route: Router, private snackbar: MatSnackBar) { }

  ngOnInit() {
  }

  registerUser() {
    this.authService.registerNewUser(this.newUser).subscribe(() => {
      this.snackbar.open("You have been successfully registered.", "", {
        duration: 3000
      });
      this.route.navigate(["/login"]);
    },
    error => {
      this.error = error.status;
    });
  }
  resetInput() {
    this.newUser.firstName = "";
    this.newUser.lastName = "";
    this.newUser.userName = "";
    this.newUser.password = "";
  }
}
