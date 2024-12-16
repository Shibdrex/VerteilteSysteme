import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { catchError, map, Observable, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  private apiUrl = 'http://localhost:5000/user/api/list-users'; // Endpoint, um Benutzerdaten zu holen
  private sessionUrl = 'http://localhost:5000/server/authenticate'; // Authentifizierungs-Endpunkt
  private sessionKey = 'userSession'; // Session key für das SessionStorage

  constructor(private http: HttpClient) {}

  // Holt die Session aus dem SessionStorage
  getSession(): any {
    const sessionData = sessionStorage.getItem(this.sessionKey);
    console.log(sessionData)
    return sessionData ? JSON.parse(sessionData) : null;
  }

  // Setzt die Session im SessionStorage
  setSession(data: any): void {

    sessionStorage.setItem(this.sessionKey, JSON.stringify(data));
  }

  // Löscht die Session im SessionStorage
  clearSession(): void {
    sessionStorage.removeItem(this.sessionKey);
  }

  // Überprüft, ob eine Session aktiv ist
  isSessionActive(): boolean {
    return !!sessionStorage.getItem(this.sessionKey);
  }

  // Holt Benutzerdaten vom Server
  getData(): Observable<any> {
    const session = this.getSession();
    const token = session ? session.jwt : null; // get jwt Token

    if (!token) {
      throw new Error('No JWT token found'); // Fehler werfen, wenn kein Token in der Session vorhanden ist
    }

    //set authorization header
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    // Server request to get user data
    return this.http.get(this.apiUrl, { headers });
  }

  // Authentication for user
  authenticate(email: string, password: string): Observable<any> {
    const body = { email, password };
    return this.http.post<any>(this.sessionUrl, body); 
  }

  // Log in with user get jwt token
  login(email: string, password: string): Observable<any> {
    return this.authenticate(email, password).pipe(
      tap((response) => {
        if (response && response.jwt) {
         
          // Holt die Benutzerdaten anhand der E-Mail-Adresse
          this.getUserByEmail(email).subscribe((userData) => {
            if (userData) {
  
              this.setSession({ email: email , userId: userData, jwt: response.jwt });
            } else {
              console.error('Benutzer-ID konnte nicht abgerufen werden');
            }
          });
        }
      })
    );
  }

  getUserByEmail(email: string): Observable<any> {
    return this.http.get<any[]>(this.apiUrl).pipe(
      catchError((error) => {
        console.error('Fehler beim Abrufen der Benutzerdaten:', error);
        return of(null);
      }),
      map((response) => {
        if (response && Array.isArray(response)) {
          const user = response.find(u => u.email === email);
          return user.id || null;
        }
        return null;
      })
    );
  }
  
  
}
