import { Component, inject, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { filter, map, Observable, Subscription, tap } from 'rxjs';
import { WebSocketService } from '../httppost.service';
import { SessionService } from '../session.service';  // Importing SessionService

@Component({
  selector: 'app-view-all-lists',
  templateUrl: './view-all-lists.component.html',
  styleUrls: ['./view-all-lists.component.scss']
})
export class ViewAllListsComponent implements OnInit {

  lists: any[] = [];
  item: string = "Test Titel"; // single list 
  currentId: number = 0;
  finalLists: Array<any> = [];
  todo: any;
  private route = inject(ActivatedRoute); //
  routSubscription!: Subscription;

  userID: number = 0;  // Variable to hold the user ID

  constructor(
    private webSocket: WebSocketService, 
    private sessionService: SessionService  // Inject SessionService
  ) { }

  getCurrentId() {
    return this.currentId = Number(this.route.snapshot.queryParamMap.get('id'));
  }

  getFilteredList() { 
    let fav = this.route.snapshot.queryParamMap.get('fav');

    if(fav === null) {
      fav = "false";  // default value for fav
    }

    if (fav === 'false') {
      this.finalLists = [...this.lists]; // returns all lists if fav is false
    } 
    else if (fav === 'true') {
      this.finalLists = this.lists.filter(item => item.favorite === true);
    }
    return this.finalLists;
  }

  ngOnInit() {
    // Retrieve the user ID from the session service
    const session = this.sessionService.getSession();
    if (session) {
      this.userID = session.id;  // Get user ID from session
    } else {
      console.log('No active session.');
    }

    this.getLists().subscribe((updatedLists) => {
      console.log('Aktualisierte Listen:', updatedLists);
      this.lists = updatedLists;
    });
  }

  getLists(): Observable<any[]> {
    const topic = '/topic/list-answer';  // Topic for CRUD response
    return this.webSocket.getReceivedMessages().pipe(
      tap((data) => {
        console.log("Empfangene Nachricht:", data);
        if (data.action === 'CREATE' || data.action === 'UPDATE' || data.action === 'DELETE') {
          console.log("CRUD-Operation erkannt:", data.action);

          // Send GET request with the userID from session
          this.webSocket.sendMessageToList({
            userID: this.userID,  // Use the session userID
            action: 'GET_ALL',
            destination: topic,
          });
        }
        if (data.action === "GET_ALL") {
          if (Array.isArray(data.lists)) {
            this.lists = data.lists;
            console.log("Endliste:", this.lists);
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
          id: item.list.id,
          title: item.list.title,
          favorite: item.list.favorite,
          user: item.list.user,
          action: item.action,
        }));

        console.log("Transformierte Daten:", transformedData);
        return transformedData;
      })
    );
  }
}
