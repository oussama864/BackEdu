jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EcoleService } from '../service/ecole.service';
import { IEcole, Ecole } from '../ecole.model';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { LocalisationService } from 'app/entities/localisation/service/localisation.service';

import { EcoleUpdateComponent } from './ecole-update.component';

describe('Component Tests', () => {
  describe('Ecole Management Update Component', () => {
    let comp: EcoleUpdateComponent;
    let fixture: ComponentFixture<EcoleUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let ecoleService: EcoleService;
    let localisationService: LocalisationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EcoleUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EcoleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EcoleUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      ecoleService = TestBed.inject(EcoleService);
      localisationService = TestBed.inject(LocalisationService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call localisation query and add missing value', () => {
        const ecole: IEcole = { id: 'CBA' };
        const localisation: ILocalisation = { id: 'Checking Gloves Right-sized' };
        ecole.localisation = localisation;

        const localisationCollection: ILocalisation[] = [{ id: 'Networked bandwidth pink' }];
        spyOn(localisationService, 'query').and.returnValue(of(new HttpResponse({ body: localisationCollection })));
        const expectedCollection: ILocalisation[] = [localisation, ...localisationCollection];
        spyOn(localisationService, 'addLocalisationToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ ecole });
        comp.ngOnInit();

        expect(localisationService.query).toHaveBeenCalled();
        expect(localisationService.addLocalisationToCollectionIfMissing).toHaveBeenCalledWith(localisationCollection, localisation);
        expect(comp.localisationsCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const ecole: IEcole = { id: 'CBA' };
        const localisation: ILocalisation = { id: 'expedite Berkshire' };
        ecole.localisation = localisation;

        activatedRoute.data = of({ ecole });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(ecole));
        expect(comp.localisationsCollection).toContain(localisation);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ecole = { id: 'ABC' };
        spyOn(ecoleService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ecole });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: ecole }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(ecoleService.update).toHaveBeenCalledWith(ecole);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ecole = new Ecole();
        spyOn(ecoleService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ecole });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: ecole }));
        saveSubject.complete();

        // THEN
        expect(ecoleService.create).toHaveBeenCalledWith(ecole);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ecole = { id: 'ABC' };
        spyOn(ecoleService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ecole });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(ecoleService.update).toHaveBeenCalledWith(ecole);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackLocalisationById', () => {
        it('Should return tracked Localisation primary key', () => {
          const entity = { id: 'ABC' };
          const trackResult = comp.trackLocalisationById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
