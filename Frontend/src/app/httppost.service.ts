import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Client } from '@stomp/stompjs';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private stompClient: Client;
  private messageReceiver: Subject<any>; // Receiver for incoming messages
  private elementReceiver: Subject<any>; // Receiver for specific element messages

  constructor() {
    
    this.stompClient = new Client({
      brokerURL: 'ws://localhost:5000/server',
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    this.messageReceiver = new Subject<any>();
    this.elementReceiver = new Subject<any>();

    this.stompClient.onConnect = () => {
      console.log('Connected to broker');






      // Subscribe to message topics

      this.stompClient.subscribe('/topic/list-answer', (message) => {
        const parsed = this.parseMessage(message);
        if (parsed) this.messageReceiver.next(parsed);
      });

      this.stompClient.subscribe("/topic/element-answer", (message) => {
        const parsed = this.parseMessage(message);
        if (parsed) this.elementReceiver.next(parsed);
      });
    };

    this.stompClient.activate();
  }

  // General utility to parse STOMP messages
  private parseMessage(message: any): any | null {
    try {
      return JSON.parse(message.body);
    } catch (error) {
      console.error('Error parsing message:', error);
      return null;
    }
  }

  // Observable for general messages
  getReceivedMessages() {
    return this.messageReceiver.asObservable();
  }

  // Observable for element-specific messages
  getElementMessages() {
    return this.elementReceiver.asObservable();
  }

  // Generalized message-sending method
  sendMessageToTopic(topic: string, message: any): void {
    if (this.stompClient.connected) {
      this.stompClient.publish({
        destination: topic,
        body: JSON.stringify(message),
      });
      console.log(`Message sent to ${topic}:`, message);
    } else {
      console.error('STOMP client not connected.');
    }
  }

  // Specific methods for sending to predefined topics
  sendMessageToList(message: any): void {
    this.sendMessageToTopic('/app/list', message);
  }

  sendMessageToElement(message: any): void {

    this.sendMessageToTopic('/app/element', message);
  }

  sendGetOneRequest(elementId: number): void {
    const message = {
      action: 'GET_ONE',
      elementID: elementId,
    };
    this.sendMessageToElement(message);
  }
}
