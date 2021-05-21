jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { LocalisationService } from '../service/localisation.service';
import { ILocalisation, Localisation } from '../localisation.model';

import { LocalisationUpdateComponent } from './localisation-update.component';

describe('Component Tests', () => {
  describe('Localisation Management Update Component', () => {
    let comp: LocalisationUpdateComponent;
    let fixture: ComponentFixture<LocalisationUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let localisationService: LocalisationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [LocalisationUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(LocalisationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LocalisationUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      localisationService = TestBed.inject(LocalisationService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const localisation: ILocalisation = { id: 'CBA' };

        activatedRoute.data = of({ localisation });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(localisation));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const localisation = { id: 'ABC' };
        spyOn(localisationService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ localisation });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: localisation }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(localisationService.update).toHaveBeenCalledWith(localisation);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const localisation = new Localisation();
        spyOn(localisationService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ localisation });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: localisation }));
        saveSubject.complete();

        // THEN
        expect(localisationService.create).toHaveBeenCalledWith(localisation);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const localisation = { id: 'ABC' };
        spyOn(localisationService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ localisation });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(localisationService.update).toHaveBeenCalledWith(localisation);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
