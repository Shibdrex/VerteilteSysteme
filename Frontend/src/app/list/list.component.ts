import { Component, Input } from '@angular/core';
import { WebSocketService } from '../httppost.service'; 

@Component({
 selector: 'app-list',
 templateUrl: './list.component.html',
 styleUrl: './list.component.scss'
})
export class ListComponent {

  private lists : any; //Variable for Lists

  private topic  = '/app/create-list'; //topic for connection with WebSocket

  @Input() title = "title";

constructor(private webSocket: WebSocketService) {}

getLists(){//gets all lists from the DB by calling the post-user-service.service
    this.lists = this.webSocket.subscribeToTopic(this.topic)
    console.log(this.lists)//controll/debug log
    return this.lists
  }

}
