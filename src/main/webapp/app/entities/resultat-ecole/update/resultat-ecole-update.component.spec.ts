jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ResultatEcoleService } from '../service/resultat-ecole.service';
import { IResultatEcole, ResultatEcole } from '../resultat-ecole.model';
import { IEcole } from 'app/entities/ecole/ecole.model';
import { EcoleService } from 'app/entities/ecole/service/ecole.service';
import { ICompetition } from 'app/entities/competition/competition.model';
import { CompetitionService } from 'app/entities/competition/service/competition.service';

import { ResultatEcoleUpdateComponent } from './resultat-ecole-update.component';

describe('Component Tests', () => {
  describe('ResultatEcole Management Update Component', () => {
    let comp: ResultatEcoleUpdateComponent;
    let fixture: ComponentFixture<ResultatEcoleUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let resultatEcoleService: ResultatEcoleService;
    let ecoleService: EcoleService;
    let competitionService: CompetitionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ResultatEcoleUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ResultatEcoleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResultatEcoleUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      resultatEcoleService = TestBed.inject(ResultatEcoleService);
      ecoleService = TestBed.inject(EcoleService);
      competitionService = TestBed.inject(CompetitionService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call ecole query and add missing value', () => {
        const resultatEcole: IResultatEcole = { id: 'CBA' };
        const ecole: IEcole = { id: 'Guinea communities' };
        resultatEcole.ecole = ecole;

        const ecoleCollection: IEcole[] = [{ id: 'fuchsia hacking' }];
        spyOn(ecoleService, 'query').and.returnValue(of(new HttpResponse({ body: ecoleCollection })));
        const expectedCollection: IEcole[] = [ecole, ...ecoleCollection];
        spyOn(ecoleService, 'addEcoleToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ resultatEcole });
        comp.ngOnInit();

        expect(ecoleService.query).toHaveBeenCalled();
        expect(ecoleService.addEcoleToCollectionIfMissing).toHaveBeenCalledWith(ecoleCollection, ecole);
        expect(comp.ecolesCollection).toEqual(expectedCollection);
      });

      it('Should call Competition query and add missing value', () => {
        const resultatEcole: IResultatEcole = { id: 'CBA' };
        const competition: ICompetition = { id: 'Industrial Pants b' };
        resultatEcole.competition = competition;

        const competitionCollection: ICompetition[] = [{ id: 'Movies Bûcherie monitor' }];
        spyOn(competitionService, 'query').and.returnValue(of(new HttpResponse({ body: competitionCollection })));
        const additionalCompetitions = [competition];
        const expectedCollection: ICompetition[] = [...additionalCompetitions, ...competitionCollection];
        spyOn(competitionService, 'addCompetitionToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ resultatEcole });
        comp.ngOnInit();

        expect(competitionService.query).toHaveBeenCalled();
        expect(competitionService.addCompetitionToCollectionIfMissing).toHaveBeenCalledWith(
          competitionCollection,
          ...additionalCompetitions
        );
        expect(comp.competitionsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const resultatEcole: IResultatEcole = { id: 'CBA' };
        const ecole: IEcole = { id: 'a content' };
        resultatEcole.ecole = ecole;
        const competition: ICompetition = { id: 'grow framework' };
        resultatEcole.competition = competition;

        activatedRoute.data = of({ resultatEcole });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(resultatEcole));
        expect(comp.ecolesCollection).toContain(ecole);
        expect(comp.competitionsSharedCollection).toContain(competition);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const resultatEcole = { id: 'ABC' };
        spyOn(resultatEcoleService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ resultatEcole });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: resultatEcole }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(resultatEcoleService.update).toHaveBeenCalledWith(resultatEcole);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const resultatEcole = new ResultatEcole();
        spyOn(resultatEcoleService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ resultatEcole });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: resultatEcole }));
        saveSubject.complete();

        // THEN
        expect(resultatEcoleService.create).toHaveBeenCalledWith(resultatEcole);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const resultatEcole = { id: 'ABC' };
        spyOn(resultatEcoleService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ resultatEcole });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(resultatEcoleService.update).toHaveBeenCalledWith(resultatEcole);
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
