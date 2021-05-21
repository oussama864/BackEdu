jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { QcmService } from '../service/qcm.service';
import { IQcm, Qcm } from '../qcm.model';
import { IConte } from 'app/entities/conte/conte.model';
import { ConteService } from 'app/entities/conte/service/conte.service';
import { ICompetition } from 'app/entities/competition/competition.model';
import { CompetitionService } from 'app/entities/competition/service/competition.service';

import { QcmUpdateComponent } from './qcm-update.component';

describe('Component Tests', () => {
  describe('Qcm Management Update Component', () => {
    let comp: QcmUpdateComponent;
    let fixture: ComponentFixture<QcmUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let qcmService: QcmService;
    let conteService: ConteService;
    let competitionService: CompetitionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [QcmUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(QcmUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QcmUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      qcmService = TestBed.inject(QcmService);
      conteService = TestBed.inject(ConteService);
      competitionService = TestBed.inject(CompetitionService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Conte query and add missing value', () => {
        const qcm: IQcm = { id: 'CBA' };
        const refcon: IConte = { id: 'a Loan User' };
        qcm.refcon = refcon;

        const conteCollection: IConte[] = [{ id: 'Centre Administrateur Bedfordshire' }];
        spyOn(conteService, 'query').and.returnValue(of(new HttpResponse({ body: conteCollection })));
        const additionalContes = [refcon];
        const expectedCollection: IConte[] = [...additionalContes, ...conteCollection];
        spyOn(conteService, 'addConteToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ qcm });
        comp.ngOnInit();

        expect(conteService.query).toHaveBeenCalled();
        expect(conteService.addConteToCollectionIfMissing).toHaveBeenCalledWith(conteCollection, ...additionalContes);
        expect(comp.contesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Competition query and add missing value', () => {
        const qcm: IQcm = { id: 'CBA' };
        const competition: ICompetition = { id: 'program Gloves Investment' };
        qcm.competition = competition;

        const competitionCollection: ICompetition[] = [{ id: 'Mouse Music alarm' }];
        spyOn(competitionService, 'query').and.returnValue(of(new HttpResponse({ body: competitionCollection })));
        const additionalCompetitions = [competition];
        const expectedCollection: ICompetition[] = [...additionalCompetitions, ...competitionCollection];
        spyOn(competitionService, 'addCompetitionToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ qcm });
        comp.ngOnInit();

        expect(competitionService.query).toHaveBeenCalled();
        expect(competitionService.addCompetitionToCollectionIfMissing).toHaveBeenCalledWith(
          competitionCollection,
          ...additionalCompetitions
        );
        expect(comp.competitionsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const qcm: IQcm = { id: 'CBA' };
        const refcon: IConte = { id: 'New b' };
        qcm.refcon = refcon;
        const competition: ICompetition = { id: 'c Anguilla open-source' };
        qcm.competition = competition;

        activatedRoute.data = of({ qcm });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(qcm));
        expect(comp.contesSharedCollection).toContain(refcon);
        expect(comp.competitionsSharedCollection).toContain(competition);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const qcm = { id: 'ABC' };
        spyOn(qcmService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ qcm });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: qcm }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(qcmService.update).toHaveBeenCalledWith(qcm);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const qcm = new Qcm();
        spyOn(qcmService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ qcm });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: qcm }));
        saveSubject.complete();

        // THEN
        expect(qcmService.create).toHaveBeenCalledWith(qcm);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const qcm = { id: 'ABC' };
        spyOn(qcmService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ qcm });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(qcmService.update).toHaveBeenCalledWith(qcm);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackConteById', () => {
        it('Should return tracked Conte primary key', () => {
          const entity = { id: 'ABC' };
          const trackResult = comp.trackConteById(0, entity);
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
