import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HTTPPostService {

  private apiUrl = 'http://localhost:5000/user/api/list-users';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':"application/json"
    })
  };

  constructor(private http: HttpClient) {}

  sendMessage(list: any[]): Observable<any> {
    console.log("New To-Do List created")
    return this.http.post(this.apiUrl, list, this.httpOptions);
  }
}
