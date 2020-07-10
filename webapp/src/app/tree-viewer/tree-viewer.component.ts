import { Component, Input, OnInit } from '@angular/core';
import { Node } from '../types';

@Component({
  selector: 'app-tree-viewer',
  templateUrl: './tree-viewer.component.html',
  styleUrls: ['./tree-viewer.component.scss']
})
export class TreeViewerComponent implements OnInit {
  @Input() nodes!: Node[];

  constructor() { }

  ngOnInit(): void {
  }

}
