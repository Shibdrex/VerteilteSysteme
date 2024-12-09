import { Component, inject, Input, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter, Subscription } from 'rxjs';

@Component({
  selector: 'app-view-all-lists',
  templateUrl: './view-all-lists.component.html',
  styleUrl: './view-all-lists.component.scss'
})
export class ViewAllListsComponent implements OnInit{

  item: string = "Test Titel" //single list 

  finalLisde: Array<any> = [];

  currentId: number | null = null; //used in ngOnInit()

  private route = inject(ActivatedRoute); //
  routSubscription!: Subscription;
  


  constructor(private router:Router){  }


  getFilteredList() {//checks if param fav is true returns all todo-lists which have favourite as true
                    //if fav is false returns all
   
    let fav = this.route.snapshot.queryParamMap.get('fav');

    if( fav === null){//default value for fav if fav doesn't appear in URL
      fav = "false"
    }
  

    if (fav === 'false') {
         this.finalLisde = [...this.lisde];//returns all lists if fav is false
    } 
    else if (fav === 'true') {
      this.finalLisde = this.lisde.filter(item => item.list.favorite === true);
    }
  
    console.log("Angezeigte Items: " + this.finalLisde)//controll/debug log
    return this.finalLisde;
  }
  

  ngOnInit() {//gets the id of the currently shown list via URL with subscription
              //the subscription watches for changes in the URL, when change accures the URl part id gets read
    
    this.routSubscription = this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))//watch changes from URLend
      .subscribe(() => {
        const id = this.route.snapshot.queryParamMap.get('id'); //get the current id part of URL
        console.log(id)                   
        this.currentId = id ? +id : null; //changes string variable to number
        console.log('Aktuelle ID:', this.currentId);
      });

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






