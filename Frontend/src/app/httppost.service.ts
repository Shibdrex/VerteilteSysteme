import { Injectable } from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private socket$!: WebSocketSubject<any>;
  private readonly serverUrl = 'ws://localhost:5000/server'; // WebSocket server url

  constructor() {}

  
  connect(): void {//creates a connection to the Websocket with the URL
    if (!this.socket$ || this.socket$.closed) {
      this.socket$ = webSocket(this.serverUrl);
      console.log('WebSocket connection opened.');
    }
  }

  
  sendMessage(topic: string, message: any): void {//Sends new created lists via WebSocketconnection
    if (this.socket$) {
      console.log(`Sending message to topic ${topic}:`, message);//controll/debug log
      this.socket$.next({ topic, message });
    } else {
      console.error('WebSocket connection is not established.');
    }
  }



  //gets all lists from db which have a certain topic at start and when new lists are created
  subscribeToTopic(topic: string): Observable<any> {
    if (!this.socket$) {
      console.error('WebSocket connection is not established.');
      throw new Error('WebSocket connection is not established.');
    }
    return new Observable((observer) => {
      this.socket$.subscribe({
        next: (msg) => {
          console.log("get lists from DB")//controll/debug log
          if (msg?.topic === topic) {     // WebSocket topic filter
            observer.next(msg.message);
            console.log(msg)
          }
        },
        error: (err) => observer.error(err),
        complete: () => observer.complete(),
      });
    });
  }

  
  closeConnection(): void {//closes WebSocket connection
    if (this.socket$) {
      this.socket$.complete();
      console.log('WebSocket connection closed.');//controll/debug log
    }
  }
}
