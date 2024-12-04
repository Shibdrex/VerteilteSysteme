import { Injectable } from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private socket$!: WebSocketSubject<any>;
  private readonly serverUrl = 'ws://localhost:5000/server'; // WebSocket-Server-URL

  constructor() {}

  /**
   * Öffnet die Verbindung zu einem WebSocket-Server
   */
  connect(): void {
    if (!this.socket$ || this.socket$.closed) {
      this.socket$ = webSocket(this.serverUrl);
      console.log('WebSocket connection opened.');
    }
  }

  /**
   * Sendet eine Nachricht an den WebSocket-Server
   * @param message Die Nachricht, die gesendet werden soll
   */
  sendMessage(message: any): void {
    if (this.socket$) {
      console.log('Sending message to server:', message);
      this.socket$.next(message);
    } else {
      console.error('WebSocket connection is not established.');
    }
  }

  /**
   * Abonniert Nachrichten von einem spezifischen Topic
   * @returns Ein Observable für eingehende Nachrichten
   */
  subscribeToTopic(): Observable<any> {
    if (!this.socket$) {
      console.error('WebSocket connection is not established.');
      throw new Error('WebSocket connection is not established.');
    }
    return this.socket$.asObservable(); // Gibt ein Observable zurück, um eingehende Nachrichten zu empfangen
  }

  /**
   * Schließt die WebSocket-Verbindung
   */
  closeConnection(): void {
    if (this.socket$) {
      this.socket$.complete();
      console.log('WebSocket connection closed.');
    }
  }
}











// import { Injectable } from '@angular/core';
// import { webSocket, WebSocketSubject } from 'rxjs/webSocket';

// @Injectable({
//   providedIn: 'root',
// })
// export class HTTPCallService {
//   private subject!: WebSocketSubject<any>; // WebSocketSubject für die Verbindung
//   private readonly serverUrl = 'ws://localhost:5000/server'; // Server-URL als Konstante

//   constructor() {
//     this.connect(); // Verbindung beim Service-Start herstellen
//   }

//   /**
//    * Öffnet die WebSocket-Verbindung
//    */
//   private connect(): void {
//     this.subject = webSocket(this.serverUrl);
//     console.log('WebSocket connection opened.');
//   }

//   /**
//    * Sendet eine Nachricht an den WebSocket-Server
//    * @param message - Die Nachricht, die gesendet werden soll
//    */
//   sendMessage(message: any): void {
//     if (!this.subject) {
//       console.error('WebSocket connection is not established.');
//       return;
//     }

//     try {
//       this.subject.next(message); // Sendet die Nachricht an den Server
//       console.log('Message sent:', message);
//     } catch (error) {
//       console.error('Failed to send message:', error);
//     }
//   }

//   /**
//    * Schließt die WebSocket-Verbindung
//    */
//   closeConnection(): void {
//     if (this.subject) {
//       this.subject.complete(); // Schließt die Verbindung
//       console.log('WebSocket connection closed.');
//     }
//   }

  
// }

























// // import { Injectable } from '@angular/core';
// // import { HTTPPostService } from './httppost.service';
// // import * as StompJs from '@stomp/stompjs';


// // @Injectable({
// //   providedIn: 'root'
// // })

// // export class HTTPCallService {


// //   private client: StompJs.Client;

// //   private connected = false;

// //  private list:any;


// //   setList(list: any): void { //takes new input from Components
// //     this.list = list; 
// //     console.log("List set in HTTPCallService: ", this.list);
// //   }

// //   constructor(private httpPostService: HTTPPostService) {
// //     this.client = new StompJs.Client({
// //       brokerURL: "ws://localhost:5000/server"
// //     })

// //     this.client.onConnect = (frame) => {
// //     console.log("connected: ", frame)
// //     this.client.subscribe("/topic/list-answer", (greeting) =>{
// //       console.log("greeting: ",greeting)
// //     })
// //   }

// //   this.client.onWebSocketError = (error) => {
// //     console.error("Bitte helfen Sie mir ich bin in Gefahr: ", error)
// //   }

// //   this.client.onStompError = (frame) => {
// //     console.error("Broker reported error: ", frame.headers["message"])
// //     console.error("Adidtional details: ", frame.body)
// //   }

// //   }
  

  

// //   async sendToKafka() { //Sends new List to Websocet through HTTPPostService
    
    
// //     console.log("enters function sendToKafka: ", this.list)
// //    this.httpPostService.sendMessage(this.list).subscribe({
// //     next: () => console.log('Message sent to Kafka successfully'),
// //     error: (err) => console.error('Error sending message to Kafka:', err),
// //    });
// //     this.client.activate()
// //     await new Promise(f => setTimeout(f,1000))
// //     this.connected = true
// //     this.client.publish({
// //       destination: "/app/list",
// //       body: JSON.stringify(this.list) 
// //     });

// //   if(this.connected) {
// //     this.client.deactivate()
// //     this.connected = false 
// //   }
// // }


// // }