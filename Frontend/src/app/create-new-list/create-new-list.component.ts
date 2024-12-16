import { Component, OnDestroy } from '@angular/core';
import { WebSocketService } from '../httppost.service';
import { SessionService } from '../session.service';  // Import the session service

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

  constructor(
    private webSocketService: WebSocketService,
    private sessionService: SessionService // Inject SessionService to access session data
  ) {
    // Listen for incoming messages from the WebSocket
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

  // Get the userID from session and send the list
  sendListToWebsocket(): void {
    const sessionData = this.sessionService.getSession();  // Retrieve session data
    if (sessionData && sessionData.userID) {
      this.message.userID = sessionData.userID;  // Set userID from session

      const createMessage = {
        userID: this.message.userID,
        action: 'CREATE',
        list: this.message.list,
      };

      this.webSocketService.sendMessageToList(createMessage);
      console.log('Liste über WebSocket gesendet:', createMessage);
    } else {
      console.error('Kein Benutzer in der Sitzung gefunden!');
    }
  }

  private handleIncomingMessage(message: any): void {
    console.log('retrieved message from WebSocket Service:', message);
    
    if (message.action === 'CREATE_RESPONSE') {
      console.log('Erstellte Liste:', message);
    }
  }

  ngOnDestroy(): void {
    console.log('CreateNewListComponent destroyed.');
  }
}
