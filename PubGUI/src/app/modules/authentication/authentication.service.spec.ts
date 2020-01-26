import { TestBed, fakeAsync, inject } from '@angular/core/testing';
import { AuthenticationService } from './authentication.service';
import { HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

const raw = {
  registerUser: {
    firstName: 'Jagjot',
    lastName: 'Singh',
    userId: 'jjsingh',
    password: 'testpass'
  },

  loginUser: {
    lastName: "",
    firstName: "",
    userId: 'jjsingh',
    password: 'testpass'
  }
}

describe('AuthenticationService', () => {
  let service: AuthenticationService;

  beforeEach(() => {

    TestBed.configureTestingModule({
      imports: [HttpClientModule, HttpClientTestingModule],
      providers: [AuthenticationService]
    });

    service = TestBed.get(AuthenticationService);

  });

  it('Should be created', () => {
    expect(service).toBeTruthy();
  });

  it('Should be able to register user data', fakeAsync(() => {
    let data = raw.registerUser;
    inject([AuthenticationService, HttpTestingController], (Backend: HttpTestingController) => {
      const mockReq = Backend.expectOne(service.backendServerAuthenticationEndpoint);
      expect(mockReq.request.url).toEqual(service.backendServerAuthenticationEndpoint, 'url should be equal to request url in service');
      expect(mockReq.request.method).toBe('POST', 'method type should be post for register');
      mockReq.flush(data);
      Backend.verify();
    });

    service.registerNewUser(data).subscribe((res: any) => {
      expect(res).toBeDefined();
      expect(res._body).toBe(data, 'response data should be same');
    });
  }));

  it('Should be able to  login user', fakeAsync(() => {
    let data = raw.loginUser;

    inject([AuthenticationService, HttpTestingController], (Backend: HttpTestingController) => {
      const mockReq = Backend.expectOne(service.backendServerAuthenticationEndpoint);
      expect(mockReq.request.url).toEqual(service.backendServerAuthenticationEndpoint, 'url should be equal to request url in service');
      expect(mockReq.request.method).toBe('POST', 'method type should be of post type for login');
      mockReq.flush(data);
      Backend.verify();
    });

    service.loginUser(data).subscribe((res: any) => {
      expect(res).toBeDefined();
      expect(res._body).toBe(data, 'response data should be same');
    });

  }));
});
