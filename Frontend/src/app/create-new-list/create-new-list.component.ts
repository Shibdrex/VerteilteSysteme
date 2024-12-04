
import { Component } from '@angular/core';
import {  WebSocketService } from '../httppost.service';




@Component({
  selector: 'app-create-new-list',
  templateUrl: './create-new-list.component.html',
 styleUrl: './create-new-list.component.scss'
})
export class CreateNewListComponent {

  //default input for new created todo lists
  private input = `{ 
    "userID": 1,
    "action": "CREATE",
    "list": {
      "title": "Neue Liste",
      "favorite": false
    }
  }`;

  private list = JSON.parse(this.input);
  private readonly topic = '/app/create-list'; // define topic for WebSocket

  constructor(private webSocketService: WebSocketService) {
    this.webSocketService.connect(); //calls httppost.service to OPEN connection with WebSocket
  }

  sendListToKafka(): void {
    console.log('Sending list to Kafka via topic:', this.topic);//controll/dubug log
    this.webSocketService.sendMessage(this.topic, this.list);
  }

  ngOnDestroy(): void {
    this.webSocketService.closeConnection(); //calls httppost.service to CLOSE connection with Websocket
  }
}


