import { Component, inject, Input, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter, map, Observable, Subscription, tap } from 'rxjs';
import { WebSocketService } from '../httppost.service';

@Component({
  selector: 'app-view-all-lists',
  templateUrl: './view-all-lists.component.html',
  styleUrl: './view-all-lists.component.scss'
})
export class ViewAllListsComponent implements OnInit{

  lists: any[] = [];

  item: string = "Test Titel" //single list 

  currentId: number = 0;

  finalLisde: Array<any> = [];

  todo: any;

  private route = inject(ActivatedRoute); //
  routSubscription!: Subscription;
  


  constructor( private webSocket: WebSocketService){  }


  getCurrentId(){

     return this.currentId = Number(this.route.snapshot.queryParamMap.get('id'));
  }


  getFilteredList() {//checks if param fav is true returns all todo-lists which have favourite as true
                    //if fav is false returns all
   
    let fav = this.route.snapshot.queryParamMap.get('fav');
    console.log(fav)

    if( fav === null){//default value for fav if fav doesn't appear in URL
      fav = "false"
    }
  

    if (fav === 'false') {
         this.finalLisde = [...this.lists];//returns all lists if fav is false
    } 
    else if (fav === 'true') {
      this.finalLisde = this.lists.filter(item => item.list.favorite === true);
    }
    return this.finalLisde;
  }
  

  ngOnInit() {//gets the id of the currently shown list via URL with subscription
              //the subscription watches for changes in the URL, when change accures the URl part id gets read
    
              this.getLists().subscribe((updatedLists) => {
                console.log('Aktualisierte Listen:', updatedLists);
                this.lists = updatedLists;
              });
            }
    


            getLists(): Observable<any[]> {
              const topic = '/topic/list-answer'; // Topic für die CRUD-Antwort
              console.log("Test")
              // Empfange Nachrichten von der WebSocket-Verbindung
              return this.webSocket.getReceivedMessages().pipe(
                tap((data) => {
                  console.log("Empfangene Nachricht:", data);
                  console.log("acrion.data: ", data.action)
                  // Überprüfe, ob die Nachricht eine CRUD-Operation ist
                  if (data.action === 'CREATE' || data.action === 'UPDATE' || data.action === 'DELETE') {
                    console.log("CRUD-Operation erkannt:", data.action);
            
                    // Sende eine GET-Anfrage über die WebSocket-Verbindung
                    this.webSocket.sendMessageToList({
                      action: 'GET_ALL',
                      destination: topic,
                    });
                  }
                  if (data.action === "GET_ALL") {
                
                    if (Array.isArray(data.lists)) {//makes sure responce is an array
                        this.lists = data.lists;
                
                        console.log("Endliste:", this.lists);  // Zeige das gespeicherte Array
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
            




lisde = [ //example list
  {
    "id": 1,
    "userID": 1,
    "action": "CREATE",
    "list": {
      "title": "Neue Liste 1",
      "favorite": false,
      "todos":[
            {"name": "parken",
            "status": true,
            "isChecked": true
            },
            {"name": "keine Ahnung",
              "status": false,
              "isChecked": true
            }
          ]
    }
  },
  {
    "id": 2,
    "userID": 2,
    "action": "CREATE",
    "list": {
      "title": "Neue Liste 2",
      "favorite": false,
      "todos":[
            {"name": "beispiel",
            "status": true,
            "isChecked": false
            },
            {"name": "putzen",
              "status": false,
              "isChecked": true
            }
          ]
    }
  },
  {
    "id": 3,
    "userID": 2,
    "action": "CREATE",
    "list": {
      "title": "Neue Liste 3",
      "favorite": true,
      "todos":[
            {"name": "laufen",
            "status": true,
            "isChecked": false
            },
            {"name": "schlafen",
              "status": false,
              "isChecked": false
            }
          ]
    }
  },
  {
    "id": 4,
    "userID": 1,
    "action": "CREATE",
    "list": {
      "title": "Neue Liste 4",
      "favorite": true,
      "todos":[
            {"name": "spaß",
            "status": true,
            "isChecked": false
            },
            {"name": "lernen",
              "status": false,
              "isChecked": true
            },
            {"name": "Hausaufgaben",
              "status": true,
              "isChecked": true
            }
          ]
    }
  },
    {
      "id": 5,
      "userID": 2,
      "action": "CREATE",
      "list": {
        "title": "Neue Liste 5",
        "favorite": true,
        "todos":[
            {"name": "einkaufen",
            "status": true,
            "isChecked": true
            },
            {"name": "Auto waschen",
              "status": false,
              "isChecked": false
            }
          ]
      }
    },
    {
      "id": 10,
      "userID": 1,
      "action": "CREATE",
      "list": {
        "title": "Neue Liste 1",
        "favorite": false,
        "todos":[
              {"name": "parken",
              "status": true,
              "isChecked": true
              },
              {"name": "keine Ahnung",
                "status": false,
                "isChecked": true
              }
            ]
      }
    }, 
    {
      "id": 11,
      "userID": 1,
      "action": "CREATE",
      "list": {
        "title": "Neue Liste 1",
        "favorite": false,
        "todos":[
              {"name": "parken",
              "status": true,
              "isChecked": true
              },
              {"name": "keine Ahnung",
                "status": false,
                "isChecked": true
              }
            ]
      }
    },
];


}






