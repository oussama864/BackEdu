jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EcolierService } from '../service/ecolier.service';
import { IEcolier, Ecolier } from '../ecolier.model';
import { ICompetition } from 'app/entities/competition/competition.model';
import { CompetitionService } from 'app/entities/competition/service/competition.service';

import { EcolierUpdateComponent } from './ecolier-update.component';

describe('Component Tests', () => {
  describe('Ecolier Management Update Component', () => {
    let comp: EcolierUpdateComponent;
    let fixture: ComponentFixture<EcolierUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let ecolierService: EcolierService;
    let competitionService: CompetitionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EcolierUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EcolierUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EcolierUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      ecolierService = TestBed.inject(EcolierService);
      competitionService = TestBed.inject(CompetitionService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Competition query and add missing value', () => {
        const ecolier: IEcolier = { id: 'CBA' };
        const competition: ICompetition = { id: 'Quality-focused' };
        ecolier.competition = competition;

        const competitionCollection: ICompetition[] = [{ id: 'task-force Saint' }];
        spyOn(competitionService, 'query').and.returnValue(of(new HttpResponse({ body: competitionCollection })));
        const additionalCompetitions = [competition];
        const expectedCollection: ICompetition[] = [...additionalCompetitions, ...competitionCollection];
        spyOn(competitionService, 'addCompetitionToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ ecolier });
        comp.ngOnInit();

        expect(competitionService.query).toHaveBeenCalled();
        expect(competitionService.addCompetitionToCollectionIfMissing).toHaveBeenCalledWith(
          competitionCollection,
          ...additionalCompetitions
        );
        expect(comp.competitionsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const ecolier: IEcolier = { id: 'CBA' };
        const competition: ICompetition = { id: 'pink' };
        ecolier.competition = competition;

        activatedRoute.data = of({ ecolier });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(ecolier));
        expect(comp.competitionsSharedCollection).toContain(competition);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ecolier = { id: 'ABC' };
        spyOn(ecolierService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ecolier });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: ecolier }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(ecolierService.update).toHaveBeenCalledWith(ecolier);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ecolier = new Ecolier();
        spyOn(ecolierService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ecolier });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: ecolier }));
        saveSubject.complete();

        // THEN
        expect(ecolierService.create).toHaveBeenCalledWith(ecolier);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ecolier = { id: 'ABC' };
        spyOn(ecolierService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ecolier });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(ecolierService.update).toHaveBeenCalledWith(ecolier);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCompetitionById', () => {
        it('Should return tracked Competition primary key', () => {
          const entity = { id: 'ABC' };
          const trackResult = comp.trackCompetitionById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
