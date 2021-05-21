import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QcmRDetailComponent } from './qcm-r-detail.component';

describe('Component Tests', () => {
  describe('QcmR Management Detail Component', () => {
    let comp: QcmRDetailComponent;
    let fixture: ComponentFixture<QcmRDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [QcmRDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ qcmR: { id: 'ABC' } }) },
          },
        ],
      })
        .overrideTemplate(QcmRDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QcmRDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load qcmR on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.qcmR).toEqual(jasmine.objectContaining({ id: 'ABC' }));
      });
    });
  });
});
