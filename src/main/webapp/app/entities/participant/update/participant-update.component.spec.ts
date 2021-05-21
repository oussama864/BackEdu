jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ParticipantService } from '../service/participant.service';
import { IParticipant, Participant } from '../participant.model';
import { IEcolier } from 'app/entities/ecolier/ecolier.model';
import { EcolierService } from 'app/entities/ecolier/service/ecolier.service';
import { IReponse } from 'app/entities/reponse/reponse.model';
import { ReponseService } from 'app/entities/reponse/service/reponse.service';
import { IResultatEcole } from 'app/entities/resultat-ecole/resultat-ecole.model';
import { ResultatEcoleService } from 'app/entities/resultat-ecole/service/resultat-ecole.service';
import { ICompetition } from 'app/entities/competition/competition.model';
import { CompetitionService } from 'app/entities/competition/service/competition.service';

import { ParticipantUpdateComponent } from './participant-update.component';

describe('Component Tests', () => {
  describe('Participant Management Update Component', () => {
    let comp: ParticipantUpdateComponent;
    let fixture: ComponentFixture<ParticipantUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let participantService: ParticipantService;
    let ecolierService: EcolierService;
    let reponseService: ReponseService;
    let resultatEcoleService: ResultatEcoleService;
    let competitionService: CompetitionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ParticipantUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ParticipantUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParticipantUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      participantService = TestBed.inject(ParticipantService);
      ecolierService = TestBed.inject(EcolierService);
      reponseService = TestBed.inject(ReponseService);
      resultatEcoleService = TestBed.inject(ResultatEcoleService);
      competitionService = TestBed.inject(CompetitionService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call ecolier query and add missing value', () => {
        const participant: IParticipant = { id: 'CBA' };
        const ecolier: IEcolier = { id: 'sensor c Plastic' };
        participant.ecolier = ecolier;

        const ecolierCollection: IEcolier[] = [{ id: 'c' }];
        spyOn(ecolierService, 'query').and.returnValue(of(new HttpResponse({ body: ecolierCollection })));
        const expectedCollection: IEcolier[] = [ecolier, ...ecolierCollection];
        spyOn(ecolierService, 'addEcolierToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ participant });
        comp.ngOnInit();

        expect(ecolierService.query).toHaveBeenCalled();
        expect(ecolierService.addEcolierToCollectionIfMissing).toHaveBeenCalledWith(ecolierCollection, ecolier);
        expect(comp.ecoliersCollection).toEqual(expectedCollection);
      });

      it('Should call reponse query and add missing value', () => {
        const participant: IParticipant = { id: 'CBA' };
        const reponse: IReponse = { id: 'HDD ROI' };
        participant.reponse = reponse;

        const reponseCollection: IReponse[] = [{ id: 'AGP' }];
        spyOn(reponseService, 'query').and.returnValue(of(new HttpResponse({ body: reponseCollection })));
        const expectedCollection: IReponse[] = [reponse, ...reponseCollection];
        spyOn(reponseService, 'addReponseToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ participant });
        comp.ngOnInit();

        expect(reponseService.query).toHaveBeenCalled();
        expect(reponseService.addReponseToCollectionIfMissing).toHaveBeenCalledWith(reponseCollection, reponse);
        expect(comp.reponsesCollection).toEqual(expectedCollection);
      });

      it('Should call ResultatEcole query and add missing value', () => {
        const participant: IParticipant = { id: 'CBA' };
        const resultatEcole: IResultatEcole = { id: 'des Sports Concrete' };
        participant.resultatEcole = resultatEcole;

        const resultatEcoleCollection: IResultatEcole[] = [{ id: 'Shoes' }];
        spyOn(resultatEcoleService, 'query').and.returnValue(of(new HttpResponse({ body: resultatEcoleCollection })));
        const additionalResultatEcoles = [resultatEcole];
        const expectedCollection: IResultatEcole[] = [...additionalResultatEcoles, ...resultatEcoleCollection];
        spyOn(resultatEcoleService, 'addResultatEcoleToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ participant });
        comp.ngOnInit();

        expect(resultatEcoleService.query).toHaveBeenCalled();
        expect(resultatEcoleService.addResultatEcoleToCollectionIfMissing).toHaveBeenCalledWith(
          resultatEcoleCollection,
          ...additionalResultatEcoles
        );
        expect(comp.resultatEcolesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Competition query and add missing value', () => {
        const participant: IParticipant = { id: 'CBA' };
        const competition: ICompetition = { id: 'wireless AI impactful' };
        participant.competition = competition;

        const competitionCollection: ICompetition[] = [{ id: 'Thailand' }];
        spyOn(competitionService, 'query').and.returnValue(of(new HttpResponse({ body: competitionCollection })));
        const additionalCompetitions = [competition];
        const expectedCollection: ICompetition[] = [...additionalCompetitions, ...competitionCollection];
        spyOn(competitionService, 'addCompetitionToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ participant });
        comp.ngOnInit();

        expect(competitionService.query).toHaveBeenCalled();
        expect(competitionService.addCompetitionToCollectionIfMissing).toHaveBeenCalledWith(
          competitionCollection,
          ...additionalCompetitions
        );
        expect(comp.competitionsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const participant: IParticipant = { id: 'CBA' };
        const ecolier: IEcolier = { id: 'convergence Card' };
        participant.ecolier = ecolier;
        const reponse: IReponse = { id: 'withdrawal Buckinghamshire' };
        participant.reponse = reponse;
        const resultatEcole: IResultatEcole = { id: 'Card Koruna' };
        participant.resultatEcole = resultatEcole;
        const competition: ICompetition = { id: 'Handmade' };
        participant.competition = competition;

        activatedRoute.data = of({ participant });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(participant));
        expect(comp.ecoliersCollection).toContain(ecolier);
        expect(comp.reponsesCollection).toContain(reponse);
        expect(comp.resultatEcolesSharedCollection).toContain(resultatEcole);
        expect(comp.competitionsSharedCollection).toContain(competition);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const participant = { id: 'ABC' };
        spyOn(participantService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ participant });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: participant }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(participantService.update).toHaveBeenCalledWith(participant);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const participant = new Participant();
        spyOn(participantService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ participant });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: participant }));
        saveSubject.complete();

        // THEN
        expect(participantService.create).toHaveBeenCalledWith(participant);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const participant = { id: 'ABC' };
        spyOn(participantService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ participant });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(participantService.update).toHaveBeenCalledWith(participant);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackEcolierById', () => {
        it('Should return tracked Ecolier primary key', () => {
          const entity = { id: 'ABC' };
          const trackResult = comp.trackEcolierById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackReponseById', () => {
        it('Should return tracked Reponse primary key', () => {
          const entity = { id: 'ABC' };
          const trackResult = comp.trackReponseById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackResultatEcoleById', () => {
        it('Should return tracked ResultatEcole primary key', () => {
          const entity = { id: 'ABC' };
          const trackResult = comp.trackResultatEcoleById(0, entity);
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
