import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IReponse, Reponse } from '../reponse.model';
import { ReponseService } from '../service/reponse.service';
import { IEcole } from 'app/entities/ecole/ecole.model';
import { EcoleService } from 'app/entities/ecole/service/ecole.service';
import { ICompetition } from 'app/entities/competition/competition.model';
import { CompetitionService } from 'app/entities/competition/service/competition.service';

@Component({
  selector: 'jhi-reponse-update',
  templateUrl: './reponse-update.component.html',
})
export class ReponseUpdateComponent implements OnInit {
  isSaving = false;

  ecolesSharedCollection: IEcole[] = [];
  competitionsSharedCollection: ICompetition[] = [];

  editForm = this.fb.group({
    id: [],
    score: [],
    createdBy: [],
    createdDate: [],
    deleted: [],
    deletedBy: [],
    deletedDate: [],
    refEcole: [],
    competition: [],
  });

  constructor(
    protected reponseService: ReponseService,
    protected ecoleService: EcoleService,
    protected competitionService: CompetitionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reponse }) => {
      this.updateForm(reponse);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reponse = this.createFromForm();
    if (reponse.id !== undefined) {
      this.subscribeToSaveResponse(this.reponseService.update(reponse));
    } else {
      this.subscribeToSaveResponse(this.reponseService.create(reponse));
    }
  }

  trackEcoleById(index: number, item: IEcole): string {
    return item.id!;
  }

  trackCompetitionById(index: number, item: ICompetition): string {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReponse>>): void {
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

  protected updateForm(reponse: IReponse): void {
    this.editForm.patchValue({
      id: reponse.id,
      score: reponse.score,
      createdBy: reponse.createdBy,
      createdDate: reponse.createdDate,
      deleted: reponse.deleted,
      deletedBy: reponse.deletedBy,
      deletedDate: reponse.deletedDate,
      refEcole: reponse.refEcole,
      competition: reponse.competition,
    });

    this.ecolesSharedCollection = this.ecoleService.addEcoleToCollectionIfMissing(this.ecolesSharedCollection, reponse.refEcole);
    this.competitionsSharedCollection = this.competitionService.addCompetitionToCollectionIfMissing(
      this.competitionsSharedCollection,
      reponse.competition
    );
  }

  protected loadRelationshipsOptions(): void {
    this.ecoleService
      .query()
      .pipe(map((res: HttpResponse<IEcole[]>) => res.body ?? []))
      .pipe(map((ecoles: IEcole[]) => this.ecoleService.addEcoleToCollectionIfMissing(ecoles, this.editForm.get('refEcole')!.value)))
      .subscribe((ecoles: IEcole[]) => (this.ecolesSharedCollection = ecoles));

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

  protected createFromForm(): IReponse {
    return {
      ...new Reponse(),
      id: this.editForm.get(['id'])!.value,
      score: this.editForm.get(['score'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      deletedBy: this.editForm.get(['deletedBy'])!.value,
      deletedDate: this.editForm.get(['deletedDate'])!.value,
      refEcole: this.editForm.get(['refEcole'])!.value,
      competition: this.editForm.get(['competition'])!.value,
    };
  }
}
