import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { SessionService } from '../session.service';

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
    private sessionService: SessionService
  ) {}

  ngOnInit(): void {
    const sessionData = sessionStorage.getItem('userSession');
    if (sessionData) {
      const session = JSON.parse(sessionData);
      this.session = !!session;
    } else {
      this.session = false;
    }

    const queryParamsSub = this.route.queryParamMap.subscribe((params) => {
      const registerParam = params.get('registriert');
      this.registrieren(registerParam);
    });
    this.subscriptions.add(queryParamsSub);
  }

  registrieren(registerParam: string | null): void {
    this.register = registerParam === '1' ? 1 : 0;
  }

  onSubmit(): void {
    this.sessionService.login(this.email, this.password).subscribe(
      (response) => {
        if (response && response.token) {
          const sessionData = {
            email: this.email,
            sessionKey: response.token, // JWT als sessionKey
          };
          this.sessionService.setSession(sessionData);

          this.session = true;
          this.router.navigate(['/list'], {
            queryParams: { session: 'true' },
          });
        } else {
          console.error('Login fehlgeschlagen: Ungültige Anmeldedaten');
        }
      },
      (error) => {
        console.error('Fehler bei der Authentifizierung:', error);
      }
    );
  }

  logout(): void {
    this.sessionService.clearSession();
    this.session = false;
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }
}
