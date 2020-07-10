import { Component, Input, OnInit } from '@angular/core';
import { Node } from '../types';
import { faMinusCircle, faPlusCircle, IconDefinition } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-tree-viewer',
  templateUrl: './tree-viewer.component.html',
  styleUrls: ['./tree-viewer.component.scss']
})
export class TreeViewerComponent {
  @Input() nodes!: Node[];
  openIds: Set<number> = new Set<number>();
  plus: IconDefinition = faPlusCircle;
  minus: IconDefinition = faMinusCircle;

  getIcon(node: Node): IconDefinition {
    return this.openIds.has(node.id) ? this.minus : this.plus;
  }

  hasChildren(node: Node): boolean {
    return node.nodes.length > 0;
  }

  toggleChildren(node: Node): void {
    const id = node.id;
    if (this.openIds.has(id)) {
      this.openIds.delete(id);
    } else {
      this.openIds.add(id);
    }
  }

  childrenShouldBeDisplayed(node: Node): boolean {
    return this.openIds.has(node.id) && this.hasChildren(node);
  }

}
