import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewGuestsComponent } from './view-guests.component';

describe('ViewGuestsComponent', () => {
  let component: ViewGuestsComponent;
  let fixture: ComponentFixture<ViewGuestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewGuestsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewGuestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
