import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PostUserServiceService } from '../post-user-service.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  public name: string = '';
  public email: string = '';
  public password: string = '';
  public confirmPassword: string = '';

  constructor(
    private postUserService: PostUserServiceService,
    private router: Router
  ) {}

  onSubmit(): void {
    if (this.password !== this.confirmPassword) {
      console.error('Passwords do not match');
      return;
    }

    const newUser = {
      name: this.name,
      email: this.email,
      password: this.hashPassword(this.password)
    };

    this.postUserService.postNewAccount(newUser).subscribe(
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
    // This should be replaced with actual bcrypt hashing on the server-side.
    return 'hashed_' + password;
  }
}
