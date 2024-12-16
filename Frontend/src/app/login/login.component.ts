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
  public session: boolean = false; // Gibt an, ob der Benutzer eingeloggt ist
  public register: number = 0; // Steuert die Anzeige der Registrierungsnachricht
  private subscriptions: Subscription = new Subscription(); // Für die Subscription Verwaltung
  public email: string = ''; // Benutzer-Email
  public password: string = ''; // Benutzer-Passwort

  constructor(
    private route: ActivatedRoute, // Zum Abrufen von URL-Parametern
    private router: Router, // Zum Navigieren
    private sessionService: SessionService // Zugriff auf den SessionService
  ) {}

  ngOnInit(): void {
    // Überprüfen, ob bereits eine Session existiert (d.h. der Benutzer ist eingeloggt)
    const sessionData = sessionStorage.getItem('userSession');
    if (sessionData) {
      const session = JSON.parse(sessionData);
      this.session = !!session; // Setzt session auf true, wenn die Session existiert
    } else {
      this.session = false; // Wenn keine Session vorhanden ist, wird false gesetzt
    }

    // Überprüfen von URL-Query-Parametern (z.B. "registriert"), um den Status der Registrierung anzuzeigen
    const queryParamsSub = this.route.queryParamMap.subscribe((params) => {
      const registerParam = params.get('registriert');
      this.registrieren(registerParam); // Registrierungsstatus setzen
    });
    this.subscriptions.add(queryParamsSub); // Subscription für späteres Abmelden speichern
  }

  // Überprüft, ob der Benutzer bereits registriert ist und setzt den Status
  registrieren(registerParam: string | null): void {
    this.register = registerParam === '1' ? 1 : 0; // Wenn der Parameter "registriert" gleich 1 ist, wird die Registrierung angezeigt
  }

  // Wird aufgerufen, wenn das Login-Formular abgesendet wird
  onSubmit(): void {
    // Versucht, den Benutzer mit der angegebenen E-Mail und Passwort einzuloggen
    this.sessionService.login(this.email, this.password).subscribe(
      (response) => {
        console.log(response.userID)
        if (response.jwt) { // Überprüft, ob das JWT in der Antwort vorhanden ist
          const sessionData = {
            userID: response.id,
            email: this.email,
            sessionKey: response.jwt, // JWT als sessionKey speichern
          };
    

          this.session = true; // Setzt die session-Variable auf true
          this.router.navigate(['/list'], { // Navigiert zur Liste von Benutzern
            queryParams: { session: 'true' },
          });

          // Jetzt, da der Benutzer eingeloggt ist, kann die getData Anfrage ausgeführt werden
          this.sessionService.getData().subscribe(
            (userData) => {
              console.log('Benutzerdaten:', userData); // Benutzerdaten erfolgreich abgerufen
            },
            (error) => {
              console.error('Fehler beim Abrufen der Benutzerdaten:', error); // Fehler beim Abrufen der Benutzerdaten
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

  ngOnDestroy(): void {
    // Abmelden der Subscription, um Speicherlecks zu vermeiden
    this.subscriptions.unsubscribe();
  }
}
