import { Component, Type } from '@angular/core';
import { HTTPPostService } from '../httppost.service';
import * as StompJs from '@stomp/stompjs';


@Component({
  selector: 'app-create-new-list',
  templateUrl: './create-new-list.component.html',
  styleUrl: './create-new-list.component.scss'
})
export class CreateNewListComponent {

  private client: StompJs.Client;

  
  //Standard values when new list is created
 input = 
  `{ 
    "userID": 1,
    "action": "CREATE",
    "list": 
      {
        "title": "Neue Liste",
        "favorite": false
      }
  }`;

  // `{ 
  //     "firstname": "Fickdich",
  //     "lastname": "j",
  //     "email": "j@l.e",
  //     "password": "jejfief"
  // }`;

  list = JSON.parse(this.input)


  connected = false


  

  constructor(private httpPostService: HTTPPostService) {
    this.client = new StompJs.Client({
      brokerURL: "ws://localhost:5000/server"
    })

    this.client.onConnect = (frame) => {
    console.log("connected: ", frame)
    this.client.subscribe("/topic/list-answer", (greeting) =>{
      console.log("greeting: ",greeting)
    })
  }

  this.client.onWebSocketError = (error) => {
    console.error("Bitte helfen Sie mir ich bin in Gefahr: ", error)
  }

  this.client.onStompError = (frame) => {
    console.error("Broker reported error: ", frame.headers["message"])
    console.error("Adidtional details: ", frame.body)
  }

  }
  

  

  async sendToKafka() { //Sends new List to Websocet through HTTPPostService 
    console.log("enters function sendToKafka: ", this.list)
   this.httpPostService.sendMessage(this.list).subscribe({
    next: () => console.log('Message sent to Kafka successfully'),
    error: (err) => console.error('Error sending message to Kafka:', err),
   });
    this.client.activate()
    await new Promise(f => setTimeout(f,1000))
    this.connected = true
    this.client.publish({
      destination: "/app/list",
      body: JSON.stringify(this.list) 
    });

  if(this.connected) {
    this.client.deactivate()
    this.connected = false 
  }
}
}
