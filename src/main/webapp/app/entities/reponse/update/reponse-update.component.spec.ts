jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ReponseService } from '../service/reponse.service';
import { IReponse, Reponse } from '../reponse.model';
import { IEcole } from 'app/entities/ecole/ecole.model';
import { EcoleService } from 'app/entities/ecole/service/ecole.service';
import { ICompetition } from 'app/entities/competition/competition.model';
import { CompetitionService } from 'app/entities/competition/service/competition.service';

import { ReponseUpdateComponent } from './reponse-update.component';

describe('Component Tests', () => {
  describe('Reponse Management Update Component', () => {
    let comp: ReponseUpdateComponent;
    let fixture: ComponentFixture<ReponseUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let reponseService: ReponseService;
    let ecoleService: EcoleService;
    let competitionService: CompetitionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ReponseUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ReponseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReponseUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      reponseService = TestBed.inject(ReponseService);
      ecoleService = TestBed.inject(EcoleService);
      competitionService = TestBed.inject(CompetitionService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Ecole query and add missing value', () => {
        const reponse: IReponse = { id: 'CBA' };
        const refEcole: IEcole = { id: 'vertical Account' };
        reponse.refEcole = refEcole;

        const ecoleCollection: IEcole[] = [{ id: 'deposit' }];
        spyOn(ecoleService, 'query').and.returnValue(of(new HttpResponse({ body: ecoleCollection })));
        const additionalEcoles = [refEcole];
        const expectedCollection: IEcole[] = [...additionalEcoles, ...ecoleCollection];
        spyOn(ecoleService, 'addEcoleToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ reponse });
        comp.ngOnInit();

        expect(ecoleService.query).toHaveBeenCalled();
        expect(ecoleService.addEcoleToCollectionIfMissing).toHaveBeenCalledWith(ecoleCollection, ...additionalEcoles);
        expect(comp.ecolesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Competition query and add missing value', () => {
        const reponse: IReponse = { id: 'CBA' };
        const competition: ICompetition = { id: 'Paris a leading-edge' };
        reponse.competition = competition;

        const competitionCollection: ICompetition[] = [{ id: 'c Rustic' }];
        spyOn(competitionService, 'query').and.returnValue(of(new HttpResponse({ body: competitionCollection })));
        const additionalCompetitions = [competition];
        const expectedCollection: ICompetition[] = [...additionalCompetitions, ...competitionCollection];
        spyOn(competitionService, 'addCompetitionToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ reponse });
        comp.ngOnInit();

        expect(competitionService.query).toHaveBeenCalled();
        expect(competitionService.addCompetitionToCollectionIfMissing).toHaveBeenCalledWith(
          competitionCollection,
          ...additionalCompetitions
        );
        expect(comp.competitionsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const reponse: IReponse = { id: 'CBA' };
        const refEcole: IEcole = { id: 'e-business Languedoc-Roussillon Analyste' };
        reponse.refEcole = refEcole;
        const competition: ICompetition = { id: 'Rubber' };
        reponse.competition = competition;

        activatedRoute.data = of({ reponse });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(reponse));
        expect(comp.ecolesSharedCollection).toContain(refEcole);
        expect(comp.competitionsSharedCollection).toContain(competition);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const reponse = { id: 'ABC' };
        spyOn(reponseService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ reponse });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: reponse }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(reponseService.update).toHaveBeenCalledWith(reponse);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const reponse = new Reponse();
        spyOn(reponseService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ reponse });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: reponse }));
        saveSubject.complete();

        // THEN
        expect(reponseService.create).toHaveBeenCalledWith(reponse);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const reponse = { id: 'ABC' };
        spyOn(reponseService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ reponse });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(reponseService.update).toHaveBeenCalledWith(reponse);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackEcoleById', () => {
        it('Should return tracked Ecole primary key', () => {
          const entity = { id: 'ABC' };
          const trackResult = comp.trackEcoleById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

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
