import { Component, OnDestroy } from '@angular/core';
import { WebSocketService } from '../httppost.service';

@Component({
  selector: 'app-create-new-list',
  templateUrl: './create-new-list.component.html',
  styleUrls: ['./create-new-list.component.scss']
})
export class CreateNewListComponent implements OnDestroy {


  private defaultInput = `{ 
    "userID": 1,
    "action": "CREATE",
    "list": {
      "title": "Neue Liste",
      "favorite": false
    }
  }`;

  private message = JSON.parse(this.defaultInput);

  constructor(private webSocketService: WebSocketService) {

  
  // Listen for incoming messages
  this.webSocketService.getReceivedMessages().subscribe({
    next: (message) => {
      console.log('Message retrieved:', message);
      this.handleIncomingMessage(message);
    },
    error: (err) => {
      console.error('Error while receiving message', err);
    },
    complete: () => {
      console.log('WebSocket-Stream done');
    }
  });
}

  
  sendListToWebsocket(): void {//further handelt in WebSocketService
    const createMessage = {
      userID: this.message.userID,
      action: 'CREATE',
      list: this.message.list,
    };

    this.webSocketService.sendMessageToList(createMessage);
    console.log('Liste über WebSocket gesendet:', createMessage);
  }

  
  private handleIncomingMessage(message: any): void {
    console.log('retrieved message from WebSocket Service:', message);

    
    if (message.action === 'CREATE_RESPONSE') {
      console.log(':', message);
    }
  }

  ngOnDestroy(): void {
    console.log('CreateNewListComponent killed.');
  }
}
