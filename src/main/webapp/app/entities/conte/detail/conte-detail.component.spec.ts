import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConteDetailComponent } from './conte-detail.component';

describe('Component Tests', () => {
  describe('Conte Management Detail Component', () => {
    let comp: ConteDetailComponent;
    let fixture: ComponentFixture<ConteDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ConteDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ conte: { id: 'ABC' } }) },
          },
        ],
      })
        .overrideTemplate(ConteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load conte on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.conte).toEqual(jasmine.objectContaining({ id: 'ABC' }));
      });
    });
  });
});
