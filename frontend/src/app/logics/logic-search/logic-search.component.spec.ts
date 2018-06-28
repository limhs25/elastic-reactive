import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LogicSearchComponent } from './logic-search.component';

describe('LogicSearchComponent', () => {
  let component: LogicSearchComponent;
  let fixture: ComponentFixture<LogicSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LogicSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LogicSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
