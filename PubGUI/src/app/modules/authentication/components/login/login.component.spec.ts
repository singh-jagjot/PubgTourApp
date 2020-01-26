import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import { AuthenticationService } from '../../authentication.service';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { SharedMaterialModule } from 'src/app/modules/shared-material/shared-material.module';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { By } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

const raw = {
  userData: {
    firstName: "john",
    lastName: "tanny",
    userName: "testuser",
    password: "qwerty"
  }
}

describe('LoginComponent', () => {
  let component: LoginComponent;
  let service: AuthenticationService
  let fixture: ComponentFixture<LoginComponent>;
  let spyUser: any;
  let routes: Router;
  let location: Location;

  class AuthenticationServiceStub {
    currentUser: any;
    constructor() {}

    login(data) {
      if(data.userName == raw.userData.userName) {
        return of(data.userId);
      }
      return of(false);
    }
  }

  class fake {
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginComponent ],
      imports: [FormsModule,SharedMaterialModule,
        RouterTestingModule.withRoutes([{path: '', component: fake}])
      ],
      providers: [{provide: AuthenticationService, useClass: AuthenticationServiceStub}]
    })
    .compileComponents();
  }));

  beforeEach(() => {

    fixture = TestBed.createComponent(LoginComponent);
    fixture.debugElement.injector.get(AuthenticationService);
    location = TestBed.get(Location);
    routes = TestBed.get(Router);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('It should create', () => {
    expect(component).toBeTruthy();
  });

  it('It should contain two input box for userName and password', () => {
    let userName = fixture.debugElement.query(By.css('.user-name'));
    let password = fixture.debugElement.query(By.css('.user-password'));
    let loginBtn = fixture.debugElement.query(By.css('.main-login-button'));
    let registerBtn = fixture.debugElement.query(By.css('.register-button'));

    expect(userName.nativeElement).toBeTruthy();
    expect(password.nativeElement).toBeTruthy();
    expect(loginBtn.nativeElement).toBeTruthy();
    expect(registerBtn.nativeElement).toBeTruthy();
  })

  it('should login in successfully', async(() => {
    let userName = fixture.debugElement.query(By.css('.user-name')).nativeElement;
    let password = fixture.debugElement.query(By.css('.user-password')).nativeElement;
    let loginBtn = fixture.debugElement.query(By.css('.main-login-button')).nativeElement;

    fixture.detectChanges();

    fixture.whenStable().then(() => {
      userName.value = 'testuser';
      password.value = 'qwerty'
      userName.dispatchEvent(new Event('input'));
      password.dispatchEvent(new Event('input'));
      loginBtn.click();

    }).then(() => {
      expect(location.path()).toBe('');
    });

  }));
});
