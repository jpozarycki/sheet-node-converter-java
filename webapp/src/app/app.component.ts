import { Component, OnInit } from '@angular/core';
import { Node } from './types';
import { DataService } from './data-service/data.service';
import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'webapp';
  nodes: Node[] | undefined;
  nodes$: Subscription | undefined;

  constructor(private readonly dataService: DataService) {}

  ngOnInit(): void {
   this.nodes$ = this.dataService.getDefaultNodes().subscribe((nodes: Node[]) => {
     this.nodes = nodes;
   });
  }
}
