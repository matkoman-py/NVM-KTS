import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewOrderWaiterComponent } from './view-order-waiter.component';

describe('ViewOrderWaiterComponent', () => {
  let component: ViewOrderWaiterComponent;
  let fixture: ComponentFixture<ViewOrderWaiterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewOrderWaiterComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewOrderWaiterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
