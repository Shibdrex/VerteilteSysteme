import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute } from '@angular/router';
import { filter } from 'rxjs/operators';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  currentUrl: string | undefined;

  constructor(private router: Router, private route: ActivatedRoute) {}
  ngOnInit(): void {

console.log('ich bin in ngOnIt')

    throw new Error('Method not implemented.');
  }

  ngOnIt(){
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: NavigationEnd)=> {
      this.currentUrl = event.urlAfterRedirects;
      console.log(this.currentUrl);
    });
  }
   
    }
