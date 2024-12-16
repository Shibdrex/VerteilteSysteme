import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private apiUrl = 'http://localhost:5000/api/authenticate';  // Your backend API URL
  private tokenKey = 'authToken';  // Key for storing JWT token in localStorage

  constructor(private http: HttpClient) {}

  // Login method that will call the backend API to authenticate the user
  login(email: string, password: string): Observable<any> {
    const loginData = { email, password };
    return this.http.post<any>(this.apiUrl, loginData).pipe(
      catchError((error) => {
        console.error('Login failed', error);
        return of(null);  // Return null or an empty observable if login fails
      })
    );
  }

  // Method to check if the user is authenticated (e.g., check if token exists)
  isAuthenticated(): boolean {
    return !!localStorage.getItem(this.tokenKey);
  }

  // Store the authentication token in localStorage
  setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  // Get the authentication token from localStorage
  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  // Log out by removing the token
  logout(): void {
    localStorage.removeItem(this.tokenKey);
  }
}
