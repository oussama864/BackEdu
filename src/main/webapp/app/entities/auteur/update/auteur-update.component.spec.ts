jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AuteurService } from '../service/auteur.service';
import { IAuteur, Auteur } from '../auteur.model';

import { AuteurUpdateComponent } from './auteur-update.component';

describe('Component Tests', () => {
  describe('Auteur Management Update Component', () => {
    let comp: AuteurUpdateComponent;
    let fixture: ComponentFixture<AuteurUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let auteurService: AuteurService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AuteurUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AuteurUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AuteurUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      auteurService = TestBed.inject(AuteurService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const auteur: IAuteur = { id: 'CBA' };

        activatedRoute.data = of({ auteur });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(auteur));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const auteur = { id: 'ABC' };
        spyOn(auteurService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ auteur });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: auteur }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(auteurService.update).toHaveBeenCalledWith(auteur);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const auteur = new Auteur();
        spyOn(auteurService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ auteur });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: auteur }));
        saveSubject.complete();

        // THEN
        expect(auteurService.create).toHaveBeenCalledWith(auteur);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const auteur = { id: 'ABC' };
        spyOn(auteurService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ auteur });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(auteurService.update).toHaveBeenCalledWith(auteur);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
