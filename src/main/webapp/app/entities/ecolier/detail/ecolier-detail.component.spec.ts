import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EcolierDetailComponent } from './ecolier-detail.component';

describe('Component Tests', () => {
  describe('Ecolier Management Detail Component', () => {
    let comp: EcolierDetailComponent;
    let fixture: ComponentFixture<EcolierDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [EcolierDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ ecolier: { id: 'ABC' } }) },
          },
        ],
      })
        .overrideTemplate(EcolierDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EcolierDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ecolier on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ecolier).toEqual(jasmine.objectContaining({ id: 'ABC' }));
      });
    });
  });
});
