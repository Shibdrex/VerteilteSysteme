import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PostUserServiceService } from '../post-user-service.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  public firstname: string = '';
  public lastname: string = '';
  public email: string = '';
  public password: string = '';
  public confirmPassword: string = '';

  constructor(
    private postUserService: PostUserServiceService,
    private router: Router
  ) {}

  onSubmit(): void {
    if (this.password !== this.confirmPassword) {//checks if both passwords are the same
      console.error('Passwords do not match');
      return;
    }

    const newUser = {
      firstname: this.firstname,
      lastname: this.lastname,
      email: this.email,
      password: this.hashPassword(this.password)
    };

    this.postUserService.postNewAccount(newUser).subscribe(//posts new user
      (response) => {
        console.log('User created successfully:', response);
        this.router.navigate(['/login']);
      },
      (error) => {
        console.error('Error creating user:', error);
      }
    );
  }

  hashPassword(password: string): string {
    return 'BCryped' + password;
  }
}
