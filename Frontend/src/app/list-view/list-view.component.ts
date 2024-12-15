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
  idParam: any;               //Params from URL
  access: boolean = false;                //access to edeting mode for title
  private routeSubscription!: Subscription;
  private messageSubscription!: Subscription;

  constructor(
    private route: ActivatedRoute,
    private webSocketService: WebSocketService
  ) {}

  ngOnInit(): void {
    // subscribe to url to get current list id
    this.routeSubscription = this.route.queryParamMap.subscribe((params) => {
      this.idParam = params.get('id');
      if (this.idParam) {
        this.urlId = +this.idParam;
        this.requestItem(this.urlId); 
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
    // const updateList = {
    //   listID: this.item.userID,
    //   action: 'UPDATE',
    //   userID: 1
    // };

    // const addElement ={
    //     userID: 1,
    //     listID: this.item.userID,
    //     action: 'CREATE',
    //     element: {
    //         name: "",
    //         status: false,
    //     }
    // }
      
    //     "element": {
    //         "id": "Integer <Generated>",
    //         "status": "Boolean",
    //         "tags": "Set<String> [Must decide on tags]",
    //         "dueDate": "Date",
    //         "name": "Varchar",
    //         "user": "ListUser <Set during creation with userID",
    //         "list": "TodoList <Set during creation with listID"
    //     }
    

    // this.webSocketService.sendMessageToList(updateList);
  }
  
  Remove(){
    const deleteList = {
        listID: this.idParam,
        action: 'DELETE',
        userID: 1
      };
    this.webSocketService.sendMessageToList(deleteList);
  }


  ChangeFav() {//change the favourite state
    let fav = false;
  
    if (this.item?.favorite === true) {
      fav = false; 
    } else {
      fav = true; 
    }
  
    const change = {
        userID: 1, 
        listID: this.idParam, 
        action: 'UPDATE', 
        list: {
          favorite: fav, 
          title: this.item?.title || '', 
        },
      }
  
    this.webSocketService.sendMessageToList(change);
  }

  UpdateTitle(){

    const newTitle = {
      userID: 1, 
      listID: this.idParam, 
      action: 'UPDATE', 
      list: {
        favorite: this.item.favorite, 
        title: this.item.title, 
      },
    }

  this.webSocketService.sendMessageToList(newTitle);

    this.access = false //resets edeting mode and switches to normal view
    return this.access

  }
  
  
  EnableEditing(){//enables edeting mode for the title
    this.access = true

    return this.access 
  }


 
}

