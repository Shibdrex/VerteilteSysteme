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
  private messageReceiver: Subject<any>;  // Subject to handle incoming messages

  constructor() {
    this.subject = webSocket('ws://localhost:5000/server');

    this.stompClient = new Client({
      brokerURL: 'ws://localhost:5000/server', // Replace with your WebSocket broker URL
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    this.messagePublisher = new Subject();
    this.messageReceiver = new Subject();  // Initialize receiver Subject

    this.stompClient.onConnect = (frame) => {
      console.log('Connected to broker:'); //frame can be shown for debuging

      // Subscribe to messagePublisher to send messages
      this.messagePublisher.subscribe({
        next: (message) => {
          if (this.stompClient.connected) {
            this.stompClient.publish({
              destination: '/app/list', // Replace with your STOMP destination
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
        console.log('Received message from STOMP topic:', message);
        this.messageReceiver.next(message);  // Push the received message to messageReceiver
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

  // Method to send messages
  sendMessage(message: any) {
    this.messagePublisher.next(message);  // Publish message to the Subject
  }

  sendlist(message: any): void {
    if (this.stompClient.connected) {
      this.stompClient.publish({
        destination: '/app/list', // Ziel-Topic
        body: JSON.stringify(message),
      });
      console.log('Nachricht gesendet:', message);
    } else {
      console.error('STOMP-Client ist nicht verbunden.');
    }
  }

  // Method to receive messages
  getReceivedMessages() {
    return this.messageReceiver.asObservable();  // Return the Observable for receiving messages
  }
}



