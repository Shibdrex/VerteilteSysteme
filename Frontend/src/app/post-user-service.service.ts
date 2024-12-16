import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SessionService } from './session.service';  // Import SessionService

@Injectable({
  providedIn: 'root'
})
export class PostUserServiceService {

  private apiUrl = 'http://localhost:5000/user/api/list-users'; // URL for DB connection

  constructor(
    private http: HttpClient,
    private sessionService: SessionService  // Inject SessionService
  ) {}

  getData(): Observable<any> {
    // Retrieve session data 
    const sessionData = this.sessionService.getSession();
    const userID = sessionData ? sessionData.userId : null;

    if (userID) {
      // Optional: Send userID in request headers or as part of the body
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${userID}`,  
      });

      // Send GET request with session data (userID) as header
      return this.http.get(this.apiUrl, { headers });
    } else {
      // Handle case when session data is not available (e.g., not logged in)
      console.error('No session found!');
      return new Observable(); // Return empty observable or handle error
    }
  }

  postNewAccount(newUser: any): Observable<any> {
    // Retrieve session data (e.g., userID or email) from sessionStorage
    const sessionData = this.sessionService.getSession();
    const userID = sessionData ? sessionData.userID : null;

    const postUrl = 'http://localhost:5000/user/api/create-user';
    const userWithSession = {
      ...newUser,
      userID: userID,  // Include userID or other session data in the request body
    };

    // Post the new user along with session data
    return this.http.post(postUrl, userWithSession);
  }
}
