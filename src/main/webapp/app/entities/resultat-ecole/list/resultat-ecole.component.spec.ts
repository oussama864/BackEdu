import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ResultatEcoleService } from '../service/resultat-ecole.service';

import { ResultatEcoleComponent } from './resultat-ecole.component';

describe('Component Tests', () => {
  describe('ResultatEcole Management Component', () => {
    let comp: ResultatEcoleComponent;
    let fixture: ComponentFixture<ResultatEcoleComponent>;
    let service: ResultatEcoleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ResultatEcoleComponent],
      })
        .overrideTemplate(ResultatEcoleComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResultatEcoleComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ResultatEcoleService);

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
      expect(comp.resultatEcoles?.[0]).toEqual(jasmine.objectContaining({ id: 'ABC' }));
    });
  });
});
