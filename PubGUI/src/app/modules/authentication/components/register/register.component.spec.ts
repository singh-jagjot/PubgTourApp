import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterComponent } from './register.component';
import { AuthenticationService } from '../../authentication.service';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { of } from 'rxjs';
import { By } from '@angular/platform-browser';
import { SharedMaterialModule } from 'src/app/modules/shared-material/shared-material.module';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';


const raw = {
  firstName: "john",
  lastName: "tanny",
  userName: "tanny1",
  password: "1234"
}

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let service: AuthenticationService;
  let fixture: ComponentFixture<RegisterComponent>;
  let spyUser: any;
  let routes: Router;
  let location: Location;

  class AuthenticationServiceStub {
    currentUser: any;
    constructor() {}
    registerUser(data) {
      if(data == raw) {
        return of(data);
      }
      return of(false);
    }
  }

  class fake {
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegisterComponent ],
      imports: [FormsModule,SharedMaterialModule,
        RouterTestingModule.withRoutes([{path: '', component: fake}])
      ],
      providers: [{provide: AuthenticationService, useClass: AuthenticationServiceStub}]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterComponent);
    fixture.debugElement.injector.get(AuthenticationService);
    location = TestBed.get(Location);
    routes = TestBed.get(Router);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('It should create', () => {
    expect(component).toBeTruthy();
  });

  it('It should get all the fields of registeration form', () => {
    let firstName = fixture.debugElement.query(By.css('.first-name'));
    let lastName = fixture.debugElement.query(By.css('.last-name'));
    let userName = fixture.debugElement.query(By.css('.user-name'));
    let password = fixture.debugElement.query(By.css('.password'));
    let registerBtn = fixture.debugElement.query(By.css('.main-register-button'));
    
    expect(firstName.nativeElement).toBeTruthy();
    expect(lastName.nativeElement).toBeTruthy();
    expect(userName.nativeElement).toBeTruthy();
    expect(password.nativeElement).toBeTruthy();
    expect(registerBtn.nativeElement).toBeTruthy();
  })

  // it('It should Register user successfully', async(() => {

  //   let firstName = fixture.debugElement.query(By.css('.first-name')).nativeElement;
  //   let lastName = fixture.debugElement.query(By.css('.last-name')).nativeElement;
  //   let userName = fixture.debugElement.query(By.css('.user-name')).nativeElement;
  //   let password = fixture.debugElement.query(By.css('.password')).nativeElement;
  //   let registerBtn = fixture.debugElement.query(By.css('.main-register-button')).nativeElement;

  //   fixture.detectChanges();

  //   fixture.whenStable().then(() => {
  //     firstName.value = 'john';
  //     lastName.value = "tanny"
  //     userName.value = 'tanny1';
  //     password.value = '1234'
  //     firstName.dispatchEvent(new Event('input'))
  //     lastName.dispatchEvent(new Event('input'))
  //     userName.dispatchEvent(new Event('input'));
  //     password.dispatchEvent(new Event('input'));
  //     registerBtn.click();

  //   }).then(() => {
  //     expect(location.path()).toBe('/');
  //   });

  // }));

});
