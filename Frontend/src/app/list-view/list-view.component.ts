import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { filter, map, Subscription, tap } from 'rxjs';
import { WebSocketService } from '../httppost.service';
import { SessionService } from '../session.service';  // Import SessionService

@Component({
  selector: 'app-list-view',
  templateUrl: './list-view.component.html',
  styleUrls: ['./list-view.component.scss']
})
export class ListViewComponent implements OnInit, OnChanges {

  urlId?: number;             // ID aus der URL
  item: any = null;           // Speichert das empfangene Item
  todos: any = null;
  idParam: any;               //Params from URL
  access: boolean = false;    //access to editing mode for title
  todoElement: any;           //saves new added element in Add()
  private routeSubscription!: Subscription;
  private messageSubscription!: Subscription;

  userID: number = 0;  // Variable to hold userID from session

  constructor(
    private route: ActivatedRoute,
    private webSocketService: WebSocketService,
    private sessionService: SessionService  // Inject SessionService
  ) {}

  ngOnInit(): void {
    // Retrieve userID from session
    const session = this.sessionService.getSession();
    if (session) {
      this.userID = session.id;  // Get userID from session
    } else {
      console.log('No active session.');
    }

    // subscribe to URL to get current list id
    this.routeSubscription = this.route.queryParamMap.subscribe((params) => {
      this.idParam = params.get('id');
      if (this.idParam) {
        this.urlId = +this.idParam;
        this.requestItem(this.urlId);
        this.requestElements(this.urlId);
      }
    });

    // Subscribe to WebSocket messages
    this.messageSubscription = this.webSocketService.getReceivedMessages().subscribe({
      next: (message) => {
        console.log('Liste Empfangen:', message);
        this.handleIncomingMessage(message);
      },
      error: (err) => {
        console.error('Fehler beim Empfang von WebSocket-Nachrichten:', err);
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
      this.getElement(); // Send request for todo-elements
    }
  }

  private requestItem(listId: number): void {
    console.log(this.userID)
    const request = {
      action: 'GET_ONE',
      listID: listId,
      userID: this.userID  // Use userID from session
    };
    this.webSocketService.sendMessageToList(request); 
  }

  private requestElements(listId: number): void {
    const request = {
      action: 'GET_ALL',
      listID: listId,
      userID: this.userID  // Use userID from session
    };
    this.webSocketService.sendMessageToElement(request);
  }

  ngOnDestroy(): void {
    // Unsubscribe from all subscriptions
    if (this.routeSubscription) this.routeSubscription.unsubscribe();
    if (this.messageSubscription) this.messageSubscription.unsubscribe();
  }

  private handleIncomingMessage(message: any): void {
    if (message.action === 'GET_ONE') {
      this.item = message.list;
      if (message.lists === null) {}
      else {
        this.todos = message.lists;
      }
      console.log("Final Item: ", this.todos);
    }
  }

  getElement() {
    const topic = '/topic/element-answer'; // Topic for CRUD response
    return this.webSocketService.getElementMessages().pipe(
      tap((data) => {
        console.log("Empfangene Elemente:", data);
        if (data.action) {
          this.webSocketService.sendMessageToElement({
            action: 'GET_ALL',
            destination: topic,
            listID: this.idParam,
            userID: this.userID  // Use userID from session
          });
        }
        if (data.action === "GET_ALL") {
          if (Array.isArray(data.lists)) {
            this.todos = data.lists;
            console.log("Endliste:", this.todos);
          } else {
            console.error("Die empfangenen Daten sind kein Array:", data.lists);
          }
        }
      }),
      filter((data) => data.action === 'GET_ALL_RESPONSE'),  // Filters only the correct response
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

  Add() {
    const addElement = {
      userID: this.userID,  // Use userID from session
      listID: this.idParam,
      action: 'CREATE',
      element: {
        name: "beispiel",
        status: true,
        tags: "standart",
        dueDate: "2024-10-11"
      }
    };
    this.webSocketService.sendMessageToElement(addElement);
  }

  Remove() {
    const deleteList = {
      listID: this.idParam,
      action: 'DELETE',
      userID: this.userID  // Use userID from session
    };
    this.webSocketService.sendMessageToList(deleteList);
  }

  ChangeFav() {
    let fav = false;

    if (this.item?.favorite === true) {
      fav = false; 
    } else {
      fav = true; 
    }

    const change = {
      userID: this.userID,  // Use userID from session
      listID: this.idParam, 
      action: 'UPDATE', 
      list: {
        favorite: fav, 
        title: this.item?.title || '', 
      }
    };

    this.webSocketService.sendMessageToList(change);
  }

  UpdateTitle() {
    this.idParam = +this.idParam;

    const newTitle = {
      userID: this.userID,  // Use userID from session
      listID: this.idParam, 
      action: 'UPDATE', 
      list: {
        favorite: this.item.favorite, 
        title: this.item.title, 
      }
    };

    this.webSocketService.sendMessageToList(newTitle);
    this.access = false;  // Reset editing mode
    return this.access;
  }

  EnableEditing() {
    this.access = true;
    return this.access;
  }
}
