import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ConteService } from '../service/conte.service';

import { ConteComponent } from './conte.component';

describe('Component Tests', () => {
  describe('Conte Management Component', () => {
    let comp: ConteComponent;
    let fixture: ComponentFixture<ConteComponent>;
    let service: ConteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ConteComponent],
      })
        .overrideTemplate(ConteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConteComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ConteService);

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
      expect(comp.contes?.[0]).toEqual(jasmine.objectContaining({ id: 'ABC' }));
    });
  });
});
