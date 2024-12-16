import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { PostUserServiceService } from '../post-user-service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit, OnDestroy {
  public session: boolean = false;
  public register: number = 0;
  private subscriptions: Subscription = new Subscription();
  public email: string = '';
  public password: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private postUserService: PostUserServiceService
  ) {}

  ngOnInit(): void {
    const queryParamsSub = this.route.queryParamMap.subscribe((params) => {
      const sessionParam = params.get('session');
      const registerParam = params.get('registriert');
      this.session = sessionParam === 'true';
      this.registrieren(registerParam);
    });
    this.subscriptions.add(queryParamsSub);
  }

  registrieren(registerParam: string | null): void {
    this.register = registerParam === '1' ? 1 : 0;
  }

  onSubmit(): void {
    this.postUserService.getData().subscribe(
      (users) => {
        const user = users.find((u: any) => u.email === this.email);
        if (user && user.password === this.password) {
          console.log('Login successful');
          this.router.navigate(['/list'], {
            queryParams: { session: 'true' }  // Set the session to true
          });
        } else {
          console.error('Invalid email or password');
        }
      },
      (error) => {
        console.error('Error fetching users:', error);
      }
    );

  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }
}
