jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ConteService } from '../service/conte.service';
import { IConte, Conte } from '../conte.model';
import { IAuteur } from 'app/entities/auteur/auteur.model';
import { AuteurService } from 'app/entities/auteur/service/auteur.service';
import { ICompetition } from 'app/entities/competition/competition.model';
import { CompetitionService } from 'app/entities/competition/service/competition.service';

import { ConteUpdateComponent } from './conte-update.component';

describe('Component Tests', () => {
  describe('Conte Management Update Component', () => {
    let comp: ConteUpdateComponent;
    let fixture: ComponentFixture<ConteUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let conteService: ConteService;
    let auteurService: AuteurService;
    let competitionService: CompetitionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ConteUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ConteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConteUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      conteService = TestBed.inject(ConteService);
      auteurService = TestBed.inject(AuteurService);
      competitionService = TestBed.inject(CompetitionService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Auteur query and add missing value', () => {
        const conte: IConte = { id: 'CBA' };
        const auteur: IAuteur = { id: 'plug-and-play' };
        conte.auteur = auteur;

        const auteurCollection: IAuteur[] = [{ id: 'Cotton' }];
        spyOn(auteurService, 'query').and.returnValue(of(new HttpResponse({ body: auteurCollection })));
        const additionalAuteurs = [auteur];
        const expectedCollection: IAuteur[] = [...additionalAuteurs, ...auteurCollection];
        spyOn(auteurService, 'addAuteurToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ conte });
        comp.ngOnInit();

        expect(auteurService.query).toHaveBeenCalled();
        expect(auteurService.addAuteurToCollectionIfMissing).toHaveBeenCalledWith(auteurCollection, ...additionalAuteurs);
        expect(comp.auteursSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Competition query and add missing value', () => {
        const conte: IConte = { id: 'CBA' };
        const competition: ICompetition = { id: 'c' };
        conte.competition = competition;

        const competitionCollection: ICompetition[] = [{ id: 'du' }];
        spyOn(competitionService, 'query').and.returnValue(of(new HttpResponse({ body: competitionCollection })));
        const additionalCompetitions = [competition];
        const expectedCollection: ICompetition[] = [...additionalCompetitions, ...competitionCollection];
        spyOn(competitionService, 'addCompetitionToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ conte });
        comp.ngOnInit();

        expect(competitionService.query).toHaveBeenCalled();
        expect(competitionService.addCompetitionToCollectionIfMissing).toHaveBeenCalledWith(
          competitionCollection,
          ...additionalCompetitions
        );
        expect(comp.competitionsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const conte: IConte = { id: 'CBA' };
        const auteur: IAuteur = { id: 'Bourgogne' };
        conte.auteur = auteur;
        const competition: ICompetition = { id: 'cultivate c value-added' };
        conte.competition = competition;

        activatedRoute.data = of({ conte });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(conte));
        expect(comp.auteursSharedCollection).toContain(auteur);
        expect(comp.competitionsSharedCollection).toContain(competition);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const conte = { id: 'ABC' };
        spyOn(conteService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ conte });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: conte }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(conteService.update).toHaveBeenCalledWith(conte);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const conte = new Conte();
        spyOn(conteService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ conte });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: conte }));
        saveSubject.complete();

        // THEN
        expect(conteService.create).toHaveBeenCalledWith(conte);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const conte = { id: 'ABC' };
        spyOn(conteService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ conte });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(conteService.update).toHaveBeenCalledWith(conte);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackAuteurById', () => {
        it('Should return tracked Auteur primary key', () => {
          const entity = { id: 'ABC' };
          const trackResult = comp.trackAuteurById(0, entity);
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
