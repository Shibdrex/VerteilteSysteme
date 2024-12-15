import { Component, Input, SimpleChanges } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { WebSocketService } from '../httppost.service';


@Component({
  selector: 'app-list-view',
  templateUrl: './list-view.component.html',

  styleUrl: './list-view.component.scss'
})


export class ListViewComponent {
  
  urlId?: number;             // ID aus der URL
  item: any = null;           // Speichert das empfangene Item
  todos: any = null;
  private routeSubscription!: Subscription;
  private messageSubscription!: Subscription;

  constructor(
    private route: ActivatedRoute,
    private webSocketService: WebSocketService
  ) {}

  ngOnInit(): void {
    // Abonniere die URL-Parameter
    this.routeSubscription = this.route.queryParamMap.subscribe((params) => {
      const idParam = params.get('id');
      if (idParam) {
        this.urlId = +idParam;
        console.log('ID aus URL:', this.urlId);
        this.requestItem(this.urlId); // Anfrage senden
      }
    });

    // Abonniere die Nachrichten vom Topic "/topic/list-answer"
    this.messageSubscription = this.webSocketService.getReceivedMessages().subscribe({
      next: (message) => {
        console.log('Liste Empfangen:', message);
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

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['id'] && changes['id'].currentValue) {
      const newId = changes['id'].currentValue;
      console.log('Eingabe-ID geändert:', newId);
      this.requestItem(newId); // Anfrage senden
    }
  }

  private requestItem(listId: number): void {
    const request = {
      action: 'GET_ONE',
      listID: listId,
      userID: 1
    };
    this.webSocketService.sendMessageToList(request); // Anfrage an "/app/list" senden
    console.log('GET_ONE-Anfrage an /app/list gesendet:', request);
  }

  ngOnDestroy(): void {
    // Unsubscribe von allen Subscriptions
    if (this.routeSubscription) this.routeSubscription.unsubscribe();
    if (this.messageSubscription) this.messageSubscription.unsubscribe();
  }

  private handleIncomingMessage(message: any): void {

    if (message.action === 'GET_ONE') {
        this.item = message.list
        if(message.lists === null){}
        else{

          this.todos = message.lists
        }
        console.log("final Item: ", this.item)
    }
  }

  Add(){

  }
  
  Remove(){
   console.log(this.item)
  
  }

  ChangeNotFav(){

  }

  ChangeFav(){

  }

}
