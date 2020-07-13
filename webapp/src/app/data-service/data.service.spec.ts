import { TestBed } from '@angular/core/testing';

import { DataService } from './data.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { environment } from '../../environments/environment';

describe('DataServiceService', () => {
  let service: DataService,
    httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [DataService]
    });
    httpTestingController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(DataService);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call http service with expected path', (done) => {
    const expectedPath = environment.api + 'getNodes',
      expectedNode = {
        id: 1,
        name: "Some name",
        nodes: []
      };

    service.getDefaultNodes().subscribe((data) => {
        expect(data[0].id).toEqual(expectedNode.id);
        expect(data[0].name).toEqual(expectedNode.name);
        expect(data[0].nodes).toEqual(expectedNode.nodes);
        done()
      }, () => {
        done.fail;
      }
    );

    const req = httpTestingController.expectOne(expectedPath);

    expect(req.request.method).toEqual('GET');

    req.flush([expectedNode]);
  })
});
