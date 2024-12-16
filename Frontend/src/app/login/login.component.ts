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
    //checks if there is a session
    const sessionData = sessionStorage.getItem('userSession');
    if (sessionData) {
      const session = JSON.parse(sessionData);
      this.session = !!session; // set session true if exists
    } else {
      this.session = false; 
    }

    //checks if registriert = 1 and the register view should be shown
    const queryParamsSub = this.route.queryParamMap.subscribe((params) => {
      const registerParam = params.get('registriert');
      this.registrieren(registerParam); 
    });
    this.subscriptions.add(queryParamsSub); 
  }

  //if pram = 1 view goes to register
  registrieren(registerParam: string | null): void {
    this.register = registerParam === '1' ? 1 : 0; 
  }

  //start to make session with login
  onSubmit(): void {
    this.sessionService.login(this.email, this.password).subscribe(
      (response) => {
        console.log(response.userID)
        if (response.jwt) { //check if jwt exists
          const sessionData = {
            userID: response.id,
            email: this.email,
            sessionKey: response.jwt, 
          };
    

          this.session = true; 
          this.router.navigate(['/list'], { //change route to user
            queryParams: { session: 'true' },
          });

          //get SessionData
          this.sessionService.getData().subscribe(
            (userData) => {
              console.log('Benutzerdaten:', userData); 
            },
            (error) => {
              console.error('Fehler beim Abrufen der Benutzerdaten:', error);
            }
          );
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
    this.sessionService.clearSession(); //kills session
    this.session = false; 
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {//kills subscription
    this.subscriptions.unsubscribe();
  }
}
