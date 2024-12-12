import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class PostUserServiceService {

private apiUrl = 'http://localhost:5000/user/api/list-users'//URL for DB connection

constructor(private http: HttpClient) {}

getData(): Observable<any> {//gets all lists that are safed in DB
return this.http.get(this.apiUrl);
}

  
}
