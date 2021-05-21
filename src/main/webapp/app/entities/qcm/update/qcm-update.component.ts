import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IQcm, Qcm } from '../qcm.model';
import { QcmService } from '../service/qcm.service';
import { IConte } from 'app/entities/conte/conte.model';
import { ConteService } from 'app/entities/conte/service/conte.service';
import { ICompetition } from 'app/entities/competition/competition.model';
import { CompetitionService } from 'app/entities/competition/service/competition.service';

@Component({
  selector: 'jhi-qcm-update',
  templateUrl: './qcm-update.component.html',
})
export class QcmUpdateComponent implements OnInit {
  isSaving = false;

  contesSharedCollection: IConte[] = [];
  competitionsSharedCollection: ICompetition[] = [];

  editForm = this.fb.group({
    id: [],
    question: [],
    choixDispo: [],
    choixCorrect: [],
    createdBy: [],
    createdDate: [],
    deleted: [],
    deletedBy: [],
    deletedDate: [],
    refcon: [],
    competition: [],
  });

  constructor(
    protected qcmService: QcmService,
    protected conteService: ConteService,
    protected competitionService: CompetitionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qcm }) => {
      this.updateForm(qcm);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const qcm = this.createFromForm();
    if (qcm.id !== undefined) {
      this.subscribeToSaveResponse(this.qcmService.update(qcm));
    } else {
      this.subscribeToSaveResponse(this.qcmService.create(qcm));
    }
  }

  trackConteById(index: number, item: IConte): string {
    return item.id!;
  }

  trackCompetitionById(index: number, item: ICompetition): string {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQcm>>): void {
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

  protected updateForm(qcm: IQcm): void {
    this.editForm.patchValue({
      id: qcm.id,
      question: qcm.question,
      choixDispo: qcm.choixDispo,
      choixCorrect: qcm.choixCorrect,
      createdBy: qcm.createdBy,
      createdDate: qcm.createdDate,
      deleted: qcm.deleted,
      deletedBy: qcm.deletedBy,
      deletedDate: qcm.deletedDate,
      refcon: qcm.refcon,
      competition: qcm.competition,
    });

    this.contesSharedCollection = this.conteService.addConteToCollectionIfMissing(this.contesSharedCollection, qcm.refcon);
    this.competitionsSharedCollection = this.competitionService.addCompetitionToCollectionIfMissing(
      this.competitionsSharedCollection,
      qcm.competition
    );
  }

  protected loadRelationshipsOptions(): void {
    this.conteService
      .query()
      .pipe(map((res: HttpResponse<IConte[]>) => res.body ?? []))
      .pipe(map((contes: IConte[]) => this.conteService.addConteToCollectionIfMissing(contes, this.editForm.get('refcon')!.value)))
      .subscribe((contes: IConte[]) => (this.contesSharedCollection = contes));

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

  protected createFromForm(): IQcm {
    return {
      ...new Qcm(),
      id: this.editForm.get(['id'])!.value,
      question: this.editForm.get(['question'])!.value,
      choixDispo: this.editForm.get(['choixDispo'])!.value,
      choixCorrect: this.editForm.get(['choixCorrect'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      deletedBy: this.editForm.get(['deletedBy'])!.value,
      deletedDate: this.editForm.get(['deletedDate'])!.value,
      refcon: this.editForm.get(['refcon'])!.value,
      competition: this.editForm.get(['competition'])!.value,
    };
  }
}
