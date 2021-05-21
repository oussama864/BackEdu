import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResultatEcoleDetailComponent } from './resultat-ecole-detail.component';

describe('Component Tests', () => {
  describe('ResultatEcole Management Detail Component', () => {
    let comp: ResultatEcoleDetailComponent;
    let fixture: ComponentFixture<ResultatEcoleDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ResultatEcoleDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ resultatEcole: { id: 'ABC' } }) },
          },
        ],
      })
        .overrideTemplate(ResultatEcoleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResultatEcoleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load resultatEcole on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.resultatEcole).toEqual(jasmine.objectContaining({ id: 'ABC' }));
      });
    });
  });
});
