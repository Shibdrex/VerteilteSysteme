import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Client } from '@stomp/stompjs';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private subject: WebSocketSubject<any>;
  private stompClient: Client;
  private messagePublisher: Subject<any>;
  private messageReceiver: Subject<any>;  // Wir erstellen ein Subject für die Nachrichten

  constructor() {
    this.subject = webSocket('ws://localhost:5000/server');

    this.stompClient = new Client({
      brokerURL: 'ws://localhost:5000/server',
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    this.messagePublisher = new Subject();
    this.messageReceiver = new Subject<any>();  // Initialisiere das messageReceiver Subject

    this.stompClient.onConnect = (frame) => {
      console.log('Connected to broker:'); //frame for debugging

      // Subscribe to the messagePublisher to send messages
      this.messagePublisher.subscribe({
        next: (message) => {
          if (this.stompClient.connected) {
            this.stompClient.publish({
              destination: '/app/list', // Ziel-Topic
              body: JSON.stringify(message),
            });
            console.log('Message sent:', message);
          } else {
            console.error('STOMP client not connected.');
          }
        }
      });

      // Subscribe to a STOMP topic for receiving messages
      this.stompClient.subscribe('/topic/list-answer', (message) => {
        try {
          console.log('Raw message received:', message);
          const parsedBody = JSON.parse(message.body);
          console.log('Parsed body:', parsedBody);
          // Hier pushen wir die empfangenen Daten ins messageReceiver
          this.messageReceiver.next(parsedBody);
        } catch (error) {
          console.error('Error parsing message body:', error);
        }
      });

      // Zum weiteren Debugging
      this.stompClient.subscribe('/topic/element-answer', (message) => {
        try {
          console.log('Raw message received from element-answer:', message);
          const parsedBody = JSON.parse(message.body);
          console.log('Parsed body from element-answer:', parsedBody);
          this.messageReceiver.next(parsedBody); // Empfange auch Nachrichten für element-answer
        } catch (error) {
          console.error('Error parsing message body from element-answer:', error);
        }
      });
    };

    this.stompClient.onWebSocketClose = () => {
      console.log('WebSocket connection closed.');
    };

    this.stompClient.onWebSocketError = (error) => {
      console.error('WebSocket error:', error);
    };

    this.stompClient.onStompError = (frame) => {
      console.error('STOMP error:', frame.headers['message']);
      console.error('Details:', frame.body);
    };

    this.stompClient.activate();
  }

  // Methode zum Empfangen von Nachrichten
  getReceivedMessages() {
    return this.messageReceiver.asObservable();  // Wir geben das Observable des Subjects zurück
  }

  sendMessage(message: any) {
    this.messagePublisher.next(message);  // Publizieren von Nachrichten zum Senden
  }

  sendlist(message: any): void {
    if (this.stompClient.connected) {
      this.stompClient.publish({
        destination: '/app/list', // Ziel-Topic
        body: JSON.stringify(message),
      });
      console.log('Message sent:', message);
    } else {
      console.error('STOMP client not connected.');
    }
  }

  sendGetOneRequest(elementId: number): void {
    if (this.stompClient.connected) {
      const message = {
        action: 'GET_ONE',
        elementID: elementId
      };

      this.stompClient.publish({
        destination: '/app/element', // Ziel-Topic für GET_ONE
        body: JSON.stringify(message),
      });
      console.log('GET_ONE request sent for ID:', elementId);
    } else {
      console.error('STOMP client not connected.');
    }
  }
}
