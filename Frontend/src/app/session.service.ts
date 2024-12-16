import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  private apiUrl = 'http://localhost:5000/user/api/list-users'; //user Endpoint
  private sessionUrl = 'http://localhost:5000/server/authenticate'; //authentication for users
  private sessionKey = 'userSession'; // Session key

  constructor(private http: HttpClient) {}

  // Holt die Session aus dem SessionStorage
  getSession(): any {
    const sessionData = sessionStorage.getItem(this.sessionKey);
    return sessionData ? JSON.parse(sessionData) : null;
  }

  // set Session after login
  setSession(data: any): void {
    sessionStorage.setItem(this.sessionKey, JSON.stringify(data));
  }

  // Clears session in Storage
  clearSession(): void {
    sessionStorage.removeItem(this.sessionKey);
  }

  
  isSessionActive(): boolean {
    return !!sessionStorage.getItem(this.sessionKey);
  }

  //get UserDate for Session
  getData(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

//get JWT Token for User
  getSessionKey(userID: number): Observable<any> {
    return this.http.get(`${this.sessionUrl}?userID=${userID}`);
  }

  // creates new user
  postNewAccount(newUser: any): Observable<any> {
    const postUrl = 'http://localhost:5000/user/api/create-user';
    return this.http.post(postUrl, newUser);
  }


  //authenticates user with emal and password
  authenticate(email: string, password: string): Observable<any> {
    const body = { email, password }; 
    return this.http.post<any>(this.sessionUrl, body);
  }

  //Login User and safes JWT 
  login(email: string, password: string): Observable<any> {
    return this.authenticate(email, password).pipe(
      //Safe token after its retrieved
      tap((response) => {
        if (response.token) {
          this.setSession({ token: response.token });
        }
      })
    );
  }
}
