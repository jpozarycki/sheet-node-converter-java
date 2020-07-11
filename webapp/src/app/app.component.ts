import { Component, OnInit } from '@angular/core';
import { Node } from './types';
import { DataService } from './data-service/data.service';
import { Observable, of, Subject } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'webapp';
  nodes$: Observable<Node[] | unknown> | undefined;
  error$: Subject<boolean> = new Subject<boolean>();

  constructor(private readonly dataService: DataService) {}

  ngOnInit(): void {
   this.nodes$ = this.dataService.getDefaultNodes().pipe(catchError(() => {
     this.error$.next(true);
     return of();
   }));
  }
}
