import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostUserServiceService {

  private apiUrl = 'http://localhost:5000/user/api/list-users'; // URL for DB connection

  constructor(private http: HttpClient) {}

  getData(): Observable<any> {
    return this.http.get(this.apiUrl); // gets all lists that are saved in DB
  }

  postNewAccount(newUser: any): Observable<any> {
    console.log("hi")
    const postUrl = 'http://localhost:5000/user/api/create-user';
    return this.http.post(postUrl, newUser);
  }
  
}
