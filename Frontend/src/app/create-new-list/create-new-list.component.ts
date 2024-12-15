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
  console.log('CreateNewListComponent: Warte auf eingehende Nachrichten...');
  
  // Listen for incoming messages
  this.webSocketService.getReceivedMessages().subscribe({
    next: (message) => {
      console.log('Nachricht empfangen:', message);
      this.handleIncomingMessage(message);
    },
    error: (err) => {
      console.error('Fehler beim Empfang von WebSocket-Nachrichten:', err);
      // Zusätzliche Fehlerbehandlung oder Maßnahmen, falls erforderlich
    },
    complete: () => {
      console.log('WebSocket-Stream abgeschlossen');
    }
  });
}

  // Sende eine neue Liste via WebSocket
  sendListToKafka(): void {
    const createMessage = {
      userID: this.message.userID,
      action: 'CREATE',
      list: this.message.list,
    };

    this.webSocketService.sendMessageToList(createMessage);
    console.log('Liste über WebSocket gesendet:', createMessage);
  }

  // Eingehende Nachrichten behandeln
  private handleIncomingMessage(message: any): void {
    console.log('Nachricht von WebSocket empfangen:', message);

    // Verarbeite CREATE-spezifische Antworten (falls nötig)
    if (message.action === 'CREATE_RESPONSE') {
      console.log('Bestätigung für CREATE erhalten:', message);
      // Optional: Benachrichtigung, UI-Update oder weitere Logik
    }
  }

  ngOnDestroy(): void {
    console.log('CreateNewListComponent zerstört.');
  }
}
