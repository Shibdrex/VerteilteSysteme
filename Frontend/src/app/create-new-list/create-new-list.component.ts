import { HttpClient } from '@angular/common/http';
import { ExternalExpr } from '@angular/compiler';
import { Component } from '@angular/core';

@Component({
  selector: 'app-create-new-list',
  templateUrl: './create-new-list.component.html',
  styleUrl: './create-new-list.component.css'
})
export class CreateNewListComponent { 

  //Standard values when new list is created
   list = `{ 
    "list": [
      {
        "name": "Neue Liste",
        "todos": {},
        "fav": false
      }
    ]
  }`;
  
 

  constructor(private http: HttpClient) {}

  sendToKafka() { //Sends new List request over Loadbalancer to Kafka
    this.http.post('http://localhost:9092/', { message: this.list })
      .subscribe({
        next: () => console.log('Message sent to Kafka successfully'),
        error: (err) => console.error('Error sending message to Kafka:', err),
      });
  }
  
}

