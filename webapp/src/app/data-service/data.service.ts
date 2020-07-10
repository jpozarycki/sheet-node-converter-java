import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Node } from '../types';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  defaultNodesPath = environment.api + 'getNodes';

  constructor(private readonly http: HttpClient) { }

  getDefaultNodes(): Observable<Node[]> {
    return this.http.get<Node[]>(this.defaultNodesPath);
  }
}
