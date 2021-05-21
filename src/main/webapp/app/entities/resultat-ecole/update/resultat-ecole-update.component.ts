import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IResultatEcole, ResultatEcole } from '../resultat-ecole.model';
import { ResultatEcoleService } from '../service/resultat-ecole.service';
import { IEcole } from 'app/entities/ecole/ecole.model';
import { EcoleService } from 'app/entities/ecole/service/ecole.service';
import { ICompetition } from 'app/entities/competition/competition.model';
import { CompetitionService } from 'app/entities/competition/service/competition.service';

@Component({
  selector: 'jhi-resultat-ecole-update',
  templateUrl: './resultat-ecole-update.component.html',
})
export class ResultatEcoleUpdateComponent implements OnInit {
  isSaving = false;

  ecolesCollection: IEcole[] = [];
  competitionsSharedCollection: ICompetition[] = [];

  editForm = this.fb.group({
    id: [],
    createdBy: [],
    createdDate: [],
    deleted: [],
    deletedBy: [],
    deletedDate: [],
    ecole: [],
    competition: [],
  });

  constructor(
    protected resultatEcoleService: ResultatEcoleService,
    protected ecoleService: EcoleService,
    protected competitionService: CompetitionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultatEcole }) => {
      this.updateForm(resultatEcole);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resultatEcole = this.createFromForm();
    if (resultatEcole.id !== undefined) {
      this.subscribeToSaveResponse(this.resultatEcoleService.update(resultatEcole));
    } else {
      this.subscribeToSaveResponse(this.resultatEcoleService.create(resultatEcole));
    }
  }

  trackEcoleById(index: number, item: IEcole): string {
    return item.id!;
  }

  trackCompetitionById(index: number, item: ICompetition): string {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResultatEcole>>): void {
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

  protected updateForm(resultatEcole: IResultatEcole): void {
    this.editForm.patchValue({
      id: resultatEcole.id,
      createdBy: resultatEcole.createdBy,
      createdDate: resultatEcole.createdDate,
      deleted: resultatEcole.deleted,
      deletedBy: resultatEcole.deletedBy,
      deletedDate: resultatEcole.deletedDate,
      ecole: resultatEcole.ecole,
      competition: resultatEcole.competition,
    });

    this.ecolesCollection = this.ecoleService.addEcoleToCollectionIfMissing(this.ecolesCollection, resultatEcole.ecole);
    this.competitionsSharedCollection = this.competitionService.addCompetitionToCollectionIfMissing(
      this.competitionsSharedCollection,
      resultatEcole.competition
    );
  }

  protected loadRelationshipsOptions(): void {
    this.ecoleService
      .query({ filter: 'resultatecole-is-null' })
      .pipe(map((res: HttpResponse<IEcole[]>) => res.body ?? []))
      .pipe(map((ecoles: IEcole[]) => this.ecoleService.addEcoleToCollectionIfMissing(ecoles, this.editForm.get('ecole')!.value)))
      .subscribe((ecoles: IEcole[]) => (this.ecolesCollection = ecoles));

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

  protected createFromForm(): IResultatEcole {
    return {
      ...new ResultatEcole(),
      id: this.editForm.get(['id'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      deletedBy: this.editForm.get(['deletedBy'])!.value,
      deletedDate: this.editForm.get(['deletedDate'])!.value,
      ecole: this.editForm.get(['ecole'])!.value,
      competition: this.editForm.get(['competition'])!.value,
    };
  }
}
