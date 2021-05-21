import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { CompetitionService } from '../service/competition.service';

import { CompetitionComponent } from './competition.component';

describe('Component Tests', () => {
  describe('Competition Management Component', () => {
    let comp: CompetitionComponent;
    let fixture: ComponentFixture<CompetitionComponent>;
    let service: CompetitionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CompetitionComponent],
      })
        .overrideTemplate(CompetitionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompetitionComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(CompetitionService);

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
      expect(comp.competitions?.[0]).toEqual(jasmine.objectContaining({ id: 'ABC' }));
    });
  });
});
