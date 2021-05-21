import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QcmDetailComponent } from './qcm-detail.component';

describe('Component Tests', () => {
  describe('Qcm Management Detail Component', () => {
    let comp: QcmDetailComponent;
    let fixture: ComponentFixture<QcmDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [QcmDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ qcm: { id: 'ABC' } }) },
          },
        ],
      })
        .overrideTemplate(QcmDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QcmDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load qcm on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.qcm).toEqual(jasmine.objectContaining({ id: 'ABC' }));
      });
    });
  });
});
