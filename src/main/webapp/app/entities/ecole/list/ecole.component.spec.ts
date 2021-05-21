import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { EcoleService } from '../service/ecole.service';

import { EcoleComponent } from './ecole.component';

describe('Component Tests', () => {
  describe('Ecole Management Component', () => {
    let comp: EcoleComponent;
    let fixture: ComponentFixture<EcoleComponent>;
    let service: EcoleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EcoleComponent],
      })
        .overrideTemplate(EcoleComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EcoleComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(EcoleService);

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
      expect(comp.ecoles?.[0]).toEqual(jasmine.objectContaining({ id: 'ABC' }));
    });
  });
});
