import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { TreeViewerComponent } from './tree-viewer/tree-viewer.component';

@NgModule({
  declarations: [
    AppComponent,
    TreeViewerComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
