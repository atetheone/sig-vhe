import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  mfaCode: string = '';
  mfaRequired: boolean = false;
  mfaEnabled: boolean = false;
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    if (this.mfaRequired) {
      this.authService.verifyMfa(this.username, this.mfaCode).subscribe(
        () => this.router.navigate(['/dashboard']),
        error => this.errorMessage = 'Invalid MFA code'
      );
    } else {
      this.authService.login(this.username, this.password).subscribe(
        response => {
          if (response.mfaRequired) {
            this.mfaRequired = true;
          } else {
            this.router.navigate(['/dashboard']);
          }
        },
        error => this.errorMessage = 'Invalid username or password'
      );
    }
  }
}
