import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { LocalisationService } from '../service/localisation.service';

import { LocalisationComponent } from './localisation.component';

describe('Component Tests', () => {
  describe('Localisation Management Component', () => {
    let comp: LocalisationComponent;
    let fixture: ComponentFixture<LocalisationComponent>;
    let service: LocalisationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [LocalisationComponent],
      })
        .overrideTemplate(LocalisationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LocalisationComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(LocalisationService);

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
      expect(comp.localisations?.[0]).toEqual(jasmine.objectContaining({ id: 'ABC' }));
    });
  });
});
