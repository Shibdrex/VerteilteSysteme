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
      console.log('Connected to STOMP broker:', frame);

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

  // Method to receive messages
  getReceivedMessages() {
    return this.messageReceiver.asObservable();  // Return the Observable for receiving messages
  }
}



//   connect(): void {
//     this.subject.subscribe({
//       next: () => console.log("Connected to WebSocket"),
//       error: (err) => console.error("WebSocket error", err),
//       complete: () => console.log("WebSocket connection closed.")
//     });
//   }

//   sendMessage(topic: string, message: any): void {
//     if (this.subject.closed) {
//       console.error("WebSocket connection is closed. Cannot send message.");
//       return;
//     }

//     const messagePayload = {
//       topic: topic,
//       message: message
//     };

//     this.publishMessage(messagePayload);
//   }

//   private publishMessage(messagePayload: any): void {
//     const publishedMessage$ = new Observable<any>((observer) => {
//       observer.next(messagePayload);
//     }).pipe(publish());

//     publishedMessage$.subscribe({
//       next: (msg) => {
//         this.subject.next(msg);
//         console.log("Message sent:", msg);
//       },
//       error: (err) => console.error("Error sending message:", err),
//       complete: () => console.log("Message published successfully.")
//     });
//   }

//   closeConnection(): void {
//     this.subject.complete();
//     console.log('WebSocket connection closed.');
//   }

//   subscribeToTopic(topic: string, action: any): Observable<any> {
//     if (this.subject.closed) {
//       console.error('WebSocket connection is not established.', action);
//       throw new Error('WebSocket connection is not established.');
//     }
    
//     this.subject.next({
//       destination: '/app/list',
//       userID: 1,
//       action: 'GET_ALL'
//     });
    
//     return new Observable((observer) => {
//       this.subject.subscribe({
//         next: msg => {
//           console.log("HAllo")
//           if (msg?.topic === topic) {
//             observer.next(msg.message);
//           }
//         },
//         error: (err) => {
//           console.error("WebSocket error:", err);
//           observer.error(err);
//         },
//         complete: () => {
//           console.log("WebSocket connection complete");
//           observer.complete();
//         }
//       });
//     });
//   }
// }
