import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { EcolierService } from '../service/ecolier.service';

import { EcolierComponent } from './ecolier.component';

describe('Component Tests', () => {
  describe('Ecolier Management Component', () => {
    let comp: EcolierComponent;
    let fixture: ComponentFixture<EcolierComponent>;
    let service: EcolierService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EcolierComponent],
      })
        .overrideTemplate(EcolierComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EcolierComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(EcolierService);

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
      expect(comp.ecoliers?.[0]).toEqual(jasmine.objectContaining({ id: 'ABC' }));
    });
  });
});
