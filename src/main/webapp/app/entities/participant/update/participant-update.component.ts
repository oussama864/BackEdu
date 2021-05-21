import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IParticipant, Participant } from '../participant.model';
import { ParticipantService } from '../service/participant.service';
import { IEcolier } from 'app/entities/ecolier/ecolier.model';
import { EcolierService } from 'app/entities/ecolier/service/ecolier.service';
import { IReponse } from 'app/entities/reponse/reponse.model';
import { ReponseService } from 'app/entities/reponse/service/reponse.service';
import { IResultatEcole } from 'app/entities/resultat-ecole/resultat-ecole.model';
import { ResultatEcoleService } from 'app/entities/resultat-ecole/service/resultat-ecole.service';
import { ICompetition } from 'app/entities/competition/competition.model';
import { CompetitionService } from 'app/entities/competition/service/competition.service';

@Component({
  selector: 'jhi-participant-update',
  templateUrl: './participant-update.component.html',
})
export class ParticipantUpdateComponent implements OnInit {
  isSaving = false;

  ecoliersCollection: IEcolier[] = [];
  reponsesCollection: IReponse[] = [];
  resultatEcolesSharedCollection: IResultatEcole[] = [];
  competitionsSharedCollection: ICompetition[] = [];

  editForm = this.fb.group({
    id: [],
    createdBy: [],
    createdDate: [],
    deleted: [],
    deletedBy: [],
    deletedDate: [],
    ecolier: [],
    reponse: [],
    resultatEcole: [],
    competition: [],
  });

  constructor(
    protected participantService: ParticipantService,
    protected ecolierService: EcolierService,
    protected reponseService: ReponseService,
    protected resultatEcoleService: ResultatEcoleService,
    protected competitionService: CompetitionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ participant }) => {
      this.updateForm(participant);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const participant = this.createFromForm();
    if (participant.id !== undefined) {
      this.subscribeToSaveResponse(this.participantService.update(participant));
    } else {
      this.subscribeToSaveResponse(this.participantService.create(participant));
    }
  }

  trackEcolierById(index: number, item: IEcolier): string {
    return item.id!;
  }

  trackReponseById(index: number, item: IReponse): string {
    return item.id!;
  }

  trackResultatEcoleById(index: number, item: IResultatEcole): string {
    return item.id!;
  }

  trackCompetitionById(index: number, item: ICompetition): string {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParticipant>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(participant: IParticipant): void {
    this.editForm.patchValue({
      id: participant.id,
      createdBy: participant.createdBy,
      createdDate: participant.createdDate,
      deleted: participant.deleted,
      deletedBy: participant.deletedBy,
      deletedDate: participant.deletedDate,
      ecolier: participant.ecolier,
      reponse: participant.reponse,
      resultatEcole: participant.resultatEcole,
      competition: participant.competition,
    });

    this.ecoliersCollection = this.ecolierService.addEcolierToCollectionIfMissing(this.ecoliersCollection, participant.ecolier);
    this.reponsesCollection = this.reponseService.addReponseToCollectionIfMissing(this.reponsesCollection, participant.reponse);
    this.resultatEcolesSharedCollection = this.resultatEcoleService.addResultatEcoleToCollectionIfMissing(
      this.resultatEcolesSharedCollection,
      participant.resultatEcole
    );
    this.competitionsSharedCollection = this.competitionService.addCompetitionToCollectionIfMissing(
      this.competitionsSharedCollection,
      participant.competition
    );
  }

  protected loadRelationshipsOptions(): void {
    this.ecolierService
      .query({ filter: 'participant-is-null' })
      .pipe(map((res: HttpResponse<IEcolier[]>) => res.body ?? []))
      .pipe(
        map((ecoliers: IEcolier[]) => this.ecolierService.addEcolierToCollectionIfMissing(ecoliers, this.editForm.get('ecolier')!.value))
      )
      .subscribe((ecoliers: IEcolier[]) => (this.ecoliersCollection = ecoliers));

    this.reponseService
      .query({ filter: 'participant-is-null' })
      .pipe(map((res: HttpResponse<IReponse[]>) => res.body ?? []))
      .pipe(
        map((reponses: IReponse[]) => this.reponseService.addReponseToCollectionIfMissing(reponses, this.editForm.get('reponse')!.value))
      )
      .subscribe((reponses: IReponse[]) => (this.reponsesCollection = reponses));

    this.resultatEcoleService
      .query()
      .pipe(map((res: HttpResponse<IResultatEcole[]>) => res.body ?? []))
      .pipe(
        map((resultatEcoles: IResultatEcole[]) =>
          this.resultatEcoleService.addResultatEcoleToCollectionIfMissing(resultatEcoles, this.editForm.get('resultatEcole')!.value)
        )
      )
      .subscribe((resultatEcoles: IResultatEcole[]) => (this.resultatEcolesSharedCollection = resultatEcoles));

    this.competitionService
      .query()
      .pipe(map((res: HttpResponse<ICompetition[]>) => res.body ?? []))
      .pipe(
        map((competitions: ICompetition[]) =>
          this.competitionService.addCompetitionToCollectionIfMissing(competitions, this.editForm.get('competition')!.value)
        )
      )
      .subscribe((competitions: ICompetition[]) => (this.competitionsSharedCollection = competitions));
  }

  protected createFromForm(): IParticipant {
    return {
      ...new Participant(),
      id: this.editForm.get(['id'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      deletedBy: this.editForm.get(['deletedBy'])!.value,
      deletedDate: this.editForm.get(['deletedDate'])!.value,
      ecolier: this.editForm.get(['ecolier'])!.value,
      reponse: this.editForm.get(['reponse'])!.value,
      resultatEcole: this.editForm.get(['resultatEcole'])!.value,
      competition: this.editForm.get(['competition'])!.value,
    };
  }
}
