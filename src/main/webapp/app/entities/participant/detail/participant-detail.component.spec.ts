import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ParticipantDetailComponent } from './participant-detail.component';

describe('Component Tests', () => {
  describe('Participant Management Detail Component', () => {
    let comp: ParticipantDetailComponent;
    let fixture: ComponentFixture<ParticipantDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ParticipantDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ participant: { id: 'ABC' } }) },
          },
        ],
      })
        .overrideTemplate(ParticipantDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParticipantDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load participant on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.participant).toEqual(jasmine.objectContaining({ id: 'ABC' }));
      });
    });
  });
});
