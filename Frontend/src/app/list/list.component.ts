import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrl: './list.component.scss'
})
export class ListComponent {
/*
  constructor(private http: HttpClient) {}

  private lists : any //Variable for Lists

  getLists() { //request message from HTTP Server
   this.http.get('http://localhost:9092/')
      .subscribe({
        next: (data) => {
          this.lists = data //DB data gets moved to variable lists
          console.log('Data fits', data)
        }
          ,
        error: (err) => {
          if(err.status === 200){
            console.log('data is fine')}
          else{
            console.log('wrong data: ', err.status)
          }
        }
      });
      
      return this.lists
    } */

}
