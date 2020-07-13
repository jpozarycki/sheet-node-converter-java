import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TreeViewerComponent } from './tree-viewer.component';
import { Node } from '../types';

describe('TreeViewerComponent', () => {
  let component: TreeViewerComponent,
    fixture: ComponentFixture<TreeViewerComponent>;
  const mockNodes: Node[] = [
    {
      id: 1,
      name: "has children",
      nodes: [
        {
          id: 2,
          name: "AA",
          nodes: []
        }
      ]
    },
    {
      id: 3,
      name: "does not have children",
      nodes: []
    }
  ];

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TreeViewerComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TreeViewerComponent);
    component = fixture.componentInstance;
    component.nodes = mockNodes;
    fixture.detectChanges();
  });

  it('should create and nodes should be inherited', () => {
    expect(component).toBeTruthy();
    expect(component.nodes).toEqual(mockNodes);
  });

  it('hasChildren returns true if node has nodes', () => {
    const hasChildren = component.hasChildren(mockNodes[0]);

    expect(hasChildren).toBeTruthy();
  });

  it('all nodes should be collapsed after init', () => {
    expect(component.openIds.size).toEqual(0);
  })

  it('hasChildren returns false if node has no nodes', () => {
    const hasChildren = component.hasChildren(mockNodes[1]);

    expect(hasChildren).toBeFalsy();
  });

  it('toggleChildren save id of node in openIds when opened ', () => {
    const node = mockNodes[0];

    component.toggleChildren(node);

    expect(component.openIds.has(node.id)).toBeTruthy();
  });

  it('toggleChildren deletes id of node from openIds when closed ', () => {
    const node = mockNodes[0];

    component.toggleChildren(node);
    component.toggleChildren(node);

    expect(component.openIds.has(node.id)).toBeFalsy();
  });

  it('childrenShouldBeDisplayed returns true if node was toggled on', () => {
    const node = mockNodes[0];

    component.toggleChildren(node);
    const childrenShouldBeDisplayed = component.childrenShouldBeDisplayed(node);

    expect(childrenShouldBeDisplayed).toBeTruthy();
  });

  it('childrenShouldBeDisplayed returns true if node was toggled off', () => {
    const node = mockNodes[0];

    component.toggleChildren(node);
    component.toggleChildren(node);
    const childrenShouldBeDisplayed = component.childrenShouldBeDisplayed(node);

    expect(childrenShouldBeDisplayed).toBeFalsy();
  });

  it('getIcon returns minus if node is toggled on', () => {
    const node = mockNodes[0];

    component.toggleChildren(node);
    const icon = component.getIcon(node);

    expect(icon).toEqual(component.minus);
  });

  it('getIcon returns plus if node is toggled off', () => {
    const node = mockNodes[0];

    component.toggleChildren(node);
    component.toggleChildren(node);
    const icon = component.getIcon(node);

    expect(icon).toEqual(component.plus);
  });
});
