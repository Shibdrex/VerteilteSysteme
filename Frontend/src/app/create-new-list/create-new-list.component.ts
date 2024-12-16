import { Component, OnDestroy } from '@angular/core';
import { WebSocketService } from '../httppost.service';
import { SessionService } from '../session.service';  

@Component({
  selector: 'app-create-new-list',
  templateUrl: './create-new-list.component.html',
  styleUrls: ['./create-new-list.component.scss']
})
export class CreateNewListComponent implements OnDestroy {

  private defaultInput = { 
    "userID": 1,
    "action": "CREATE",
    "list": {
      "title": "Neue Liste",
      "favorite": false
    }
  };

  private message = { ...this.defaultInput };  // object copy

  constructor(
    private webSocketService: WebSocketService,
    private sessionService: SessionService // Injection to get Session data
  ) {
    // Wget websocket message
    this.webSocketService.getReceivedMessages().subscribe({
      next: (message) => {
        console.log('Nachricht empfangen:', message);
        this.handleIncomingMessage(message);
      },
      error: (err) => {
        console.error('Fehler beim Empfang der Nachricht', err);
      },
      complete: () => {
        console.log('WebSocket-Stream beendet');
      }
    });
  }

  // Get the userID from session and send the list
  sendListToWebsocket(): void {
    const sessionData = this.sessionService.getSession();  
    if (sessionData && sessionData.userId) {
      this.message.userID = sessionData.userId;  

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
    console.log('Nachricht vom WebSocket Service:', message);
    
    if (message.action === 'CREATE_RESPONSE') {
      console.log('Erstellte Liste:', message);
    }
  }

  ngOnDestroy(): void {
    console.log('CreateNewListComponent wurde zerstört.');
  }
}
