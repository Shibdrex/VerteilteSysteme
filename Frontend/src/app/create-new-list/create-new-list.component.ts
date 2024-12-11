
import { Component, OnDestroy } from '@angular/core';
import { WebSocketService } from '../httppost.service';

@Component({
  selector: 'app-create-new-list',
  templateUrl: './create-new-list.component.html',
  styleUrls: ['./create-new-list.component.scss']
})
export class CreateNewListComponent implements OnDestroy {
  private input = `{ 
    "userID": 1,
    "action": "CREATE",
    "list": {
      "title": "Neue Liste",
      "favorite": false
    }
  }`;
  private message = JSON.parse(this.input);

  constructor(private webSocketService: WebSocketService) {
    // Listen for incoming messages
    this.webSocketService.getReceivedMessages().subscribe({
      next: (message) => this.handleIncomingMessage(message),
      error: (err) => console.error('Error receiving WebSocket message:', err),
    });
  }

  sendListToKafka(): void {
    this.webSocketService.sendMessage(this.message);
    console.log('List sent via WebSocket:', this.message);
  }

  handleIncomingMessage(message: any): void {
    console.log('Message received from WebSocket:', message);
    // Optionally, add custom logic to handle the message here
  }

  ngOnDestroy(): void {
    // No explicit connection closing required, STOMP handles reconnections
    console.log('CreateNewListComponent destroyed.');
  }
}
