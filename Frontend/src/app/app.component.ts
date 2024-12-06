
import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter, map, Observable, Subscription } from 'rxjs';




@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',

  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit{

  title = 'Frontend';
  item: string = "Test Titel"

  filter$!: Observable<number> ;
  currentId: number | null = null;

  private route = inject(ActivatedRoute);
  routSubscription!: Subscription;
  
  paramValue!: string| null;

  constructor(private router:Router){  }

  ngOnInit() {
    this.paramValue = this.route.snapshot.paramMap.get('id'); console.log(this.paramValue);
    console.log("Das ist paramValue: " + this.paramValue)
    this.routSubscription = this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe(() => {
        const id = this.route.snapshot.queryParamMap.get('id');
        console.log(id)
        this.currentId = id ? +id : null;
        console.log('Aktuelle ID:', this.currentId);
      });
  
  }
  
  



  lisde = [
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
              "isChecked": true
              },
              {"name": "putzen",
                "status": false,
                "isChecked": false
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
              "isChecked": true
              },
              {"name": "lernen",
                "status": false,
                "isChecked": false
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
      }
  ];


}


 

