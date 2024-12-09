import { Injectable } from '@angular/core';
import { webSocket } from 'rxjs/webSocket';
import { Observable } from 'rxjs';
import { WebSocketSubject } from 'rxjs/internal/observable/dom/WebSocketSubject'; 

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {


subject: WebSocketSubject<any> =  webSocket('ws://localhost:5000/server');

connect(): void {//creates a connection to the Websocket with the URL
      this.subject.subscribe({
        next: () => console.log("Connected"),
      error: (err) => console.error("WebSocket error", err),
      complete: () => console.log("WebSocket connection closed.")
    });
  }

  sendMessage(topic: string, message: any): void {//Sends new created lists via WebSocketconnection
    if (this.subject.closed) { 
      console.error("WebSocket connection is closed. Cannot send message.");
      return;
    }
    this.subject.next({ topic, message });
    console.log("Message sent:", topic, message)
  }

  closeConnection(): void {//closes WebSocket connection
        this.subject.complete()
        console.log('WebSocket connection closed.');//controll/debug log
      }


      // //gets all lists from db which have a certain topic at start and when new lists are created
  subscribeToTopic(topic: string): Observable<any> {
    if (this.subject.closed) {
      console.error('WebSocket connection is not established.');
      throw new Error('WebSocket connection is not established.');
    }
    return new Observable((observer) => {
      this.subject.subscribe({
        next: (msg) => {
          console.log("Received message from DB");
          if (msg?.topic === topic) {  // WebSocket topic filter
            observer.next(msg.message);
            console.log(msg);
          }
        },
        error: (err) => {
          console.error("WebSocket error:", err);
          observer.error(err);
        },
        complete: () => {
          console.log("WebSocket connection complete");
          observer.complete();
        }
      });
    });
  }


}
 