import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArukFilterComponent } from './aruk-filter.component';

describe('ArukFilterComponent', () => {
  let component: ArukFilterComponent;
  let fixture: ComponentFixture<ArukFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArukFilterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArukFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
