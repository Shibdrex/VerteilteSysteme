import { Component, Input, OnChanges, SimpleChanges, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { WebSocketService } from '../httppost.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnChanges, OnInit {
  @Input() title?: string;
  @Input() id?: number;  // Optionaler Input für ID
  urlId?: number;        // ID aus der URL

  private routeSubscription!: Subscription;

  constructor(private route: ActivatedRoute, private webSocketService: WebSocketService) {}

  ngOnInit(): void {
    this.routeSubscription = this.route.queryParamMap.subscribe(params => {
      const idParam = params.get('id'); 
      if (idParam) {
        this.urlId = +idParam;
        console.log('Aktualisierte ID aus den Query-Parametern:', this.urlId);

        this.webSocketService.sendMessage({
          action: 'GET_ONE',
          id: this.urlId,
        });
      } else {
        console.warn('Keine ID in den Query-Parametern gefunden.');
      }
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['id']) {
      const currentValue = changes['id'].currentValue;

      if (this.urlId === currentValue) {
        console.log('URL-ID und Eingabe-ID stimmen überein');
        if (this.urlId !== undefined) {
          this.webSocketService.sendMessage({
            action: 'GET_ONE',
            id: this.urlId,
          });
        }
      }
    }
  }

  ngOnDestroy(): void {
    if (this.routeSubscription) {
      this.routeSubscription.unsubscribe();
    }
  }
}
