import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AuteurDetailComponent } from './auteur-detail.component';

describe('Component Tests', () => {
  describe('Auteur Management Detail Component', () => {
    let comp: AuteurDetailComponent;
    let fixture: ComponentFixture<AuteurDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AuteurDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ auteur: { id: 'ABC' } }) },
          },
        ],
      })
        .overrideTemplate(AuteurDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AuteurDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load auteur on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.auteur).toEqual(jasmine.objectContaining({ id: 'ABC' }));
      });
    });
  });
});
