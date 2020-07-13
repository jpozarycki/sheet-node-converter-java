import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { DataService } from './data-service/data.service';
import { of, throwError } from 'rxjs';

describe('AppComponent', () => {
  let fixture: ComponentFixture<AppComponent>,
    app: AppComponent,
    dataService: DataService;
  const mockNodes = [
    {
      id: 1,
      name: "Some name",
      nodes: []
    }
  ];

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [
        AppComponent
      ],
      providers: [
        DataService
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    dataService = TestBed.inject(DataService);
    app = fixture.componentInstance;
  }));

  it('should create the app', () => {
    expect(app).toBeTruthy();
  });

  it('should fetch nodes on init', () => {
    const dataServiceSpy = createDataServiceGetDefaultNodesSpy();
    app.ngOnInit();

    app.nodes$.subscribe((nodes) => {
      expect(nodes).toEqual(mockNodes);
    })
    expect(dataServiceSpy).toHaveBeenCalled();
  })

  it('should set error$ true when fetching failed', () => {
    const dataServiceSpy = createDataServiceGetDefaultNodesErrorSpy();
    app.ngOnInit();

    app.error$.subscribe(isError => {
      expect(isError).toBeTruthy();
    });
    expect(dataServiceSpy).toHaveBeenCalled();
  })

  function createDataServiceGetDefaultNodesSpy() {
    return spyOn(dataService, 'getDefaultNodes').and.callFake(() => {
      return of(mockNodes);
    });
  }

  function createDataServiceGetDefaultNodesErrorSpy() {
    return spyOn(dataService, 'getDefaultNodes').and.callFake(() => {
      return throwError(new Error("Some error"));
    });
  }
});
