import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditTableLayoutComponent } from './edit-table-layout.component';

describe('EditTableLayoutComponent', () => {
  let component: EditTableLayoutComponent;
  let fixture: ComponentFixture<EditTableLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditTableLayoutComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditTableLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
