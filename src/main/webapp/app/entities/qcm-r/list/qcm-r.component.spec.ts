import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { QcmRService } from '../service/qcm-r.service';

import { QcmRComponent } from './qcm-r.component';

describe('Component Tests', () => {
  describe('QcmR Management Component', () => {
    let comp: QcmRComponent;
    let fixture: ComponentFixture<QcmRComponent>;
    let service: QcmRService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [QcmRComponent],
      })
        .overrideTemplate(QcmRComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QcmRComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(QcmRService);

      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ id: 'ABC' }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.qcmRS?.[0]).toEqual(jasmine.objectContaining({ id: 'ABC' }));
    });
  });
});
