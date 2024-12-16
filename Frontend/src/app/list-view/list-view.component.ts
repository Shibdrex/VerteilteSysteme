import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { filter, map, Subscription, tap } from 'rxjs';
import { WebSocketService } from '../httppost.service';


@Component({
  selector: 'app-list-view',
  templateUrl: './list-view.component.html',

  styleUrl: './list-view.component.scss'
})


export class ListViewComponent implements OnInit, OnChanges{
  
  urlId?: number;             // ID aus der URL
  item: any = null;           // Speichert das empfangene Item
  todos: any = null;
  idParam: any;               //Params from URL
  access: boolean = false;    //access to edeting mode for title
  todoElement: any;           //safes new added element in Add()
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
        this.requestElements(this.urlId);
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
      this.requestItem(newId); // Send request for list
      this.getElement() //send request for todo-elements
    }
  }

  private requestItem(listId: number): void {
    const request = {
      action: 'GET_ONE',
      listID: listId,
      userID: 1
    };
    this.webSocketService.sendMessageToList(request); 
  }

  private requestElements(listId: number): void{
    const request = {
      action: 'GET_ALL',
      listID: listId,
      userID: 1
    };
    this.webSocketService.sendMessageToElement(request);
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
            console.log("Hi ich bin hier!!!!!!!!!!")
          this.todos = message.lists
        }
        console.log("final Item: ", this.todos)
    }
  }


getElement(){
   const topic = '/topic/element-answer'; // Topic für die CRUD-Antwort
                // Empfange Nachrichten von der WebSocket-Verbindung
                return this.webSocketService.getElementMessages().pipe(
                  tap((data) => {
                    console.log("Empfangene Elemente :", data);
                    console.log("acrion.data: ", data.action)
                   
                    if (data.action) {
              
                      console.log(this.idParam)
                      this.webSocketService.sendMessageToElement({
                        action: 'GET_ALL',
                        destination: topic,
                        listID: this.idParam
                      });
                    }
                    if (data.action === "GET_ALL") {
                  
                      if (Array.isArray(data.lists)) {//makes sure responce is an array
                          this.todos = data.lists;
                  
                          console.log("Endliste:", this.todos);  // Zeige das gespeicherte Array
                      } else {
                          console.error("Die empfangenen Daten sind kein Array:", data.lists);
                      }
                  }
                  }),
                  filter((data) => data.action === 'GET_ALL_RESPONSE'), //filters only the right respose
                  map((data: any[]) => {
                    if (!Array.isArray(data)) {
                      console.error("Empfangene Daten sind kein Array:", data);
                      return [];
                    }
              
                    const transformedData = data.map((item) => ({
                      id: item.element.id,
                      name: item.element.name,
                      status: item.element.status,
                    }));
              
                    console.log("Transformierte Daten:", transformedData);
                    return transformedData;
                  })
                );
              
              
}




  Add(){

    const addElement ={
        userID: 1,
        listID: this.idParam,
        action: 'CREATE',
        element:  {
          "name": "beispiel",
          "status": true,
          "tags": "standart",
          "dueDate": "2024-10-11"
          }
    }
      
    this.webSocketService.sendMessageToElement(addElement);
  }
  
  Remove(){//delets list from DB
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
    this.idParam = +this.idParam

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

