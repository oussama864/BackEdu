import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEcolier, Ecolier } from '../ecolier.model';
import { EcolierService } from '../service/ecolier.service';
import { ICompetition } from 'app/entities/competition/competition.model';
import { CompetitionService } from 'app/entities/competition/service/competition.service';

@Component({
  selector: 'jhi-ecolier-update',
  templateUrl: './ecolier-update.component.html',
})
export class EcolierUpdateComponent implements OnInit {
  isSaving = false;

  competitionsSharedCollection: ICompetition[] = [];

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    email: [],
    refUser: [],
    age: [],
    niveau: [],
    ecole: [],
    dateDeNaissance: [],
    nomParent: [],
    password: [],
    createdBy: [],
    createdDate: [],
    deleted: [],
    deletedBy: [],
    deletedDate: [],
    competition: [],
  });

  constructor(
    protected ecolierService: EcolierService,
    protected competitionService: CompetitionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ecolier }) => {
      this.updateForm(ecolier);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ecolier = this.createFromForm();
    if (ecolier.id !== undefined) {
      this.subscribeToSaveResponse(this.ecolierService.update(ecolier));
    } else {
      this.subscribeToSaveResponse(this.ecolierService.create(ecolier));
    }
  }

  trackCompetitionById(index: number, item: ICompetition): string {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEcolier>>): void {
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

  protected updateForm(ecolier: IEcolier): void {
    this.editForm.patchValue({
      id: ecolier.id,
      firstName: ecolier.firstName,
      lastName: ecolier.lastName,
      email: ecolier.email,
      refUser: ecolier.refUser,
      age: ecolier.age,
      niveau: ecolier.niveau,
      ecole: ecolier.ecole,
      dateDeNaissance: ecolier.dateDeNaissance,
      nomParent: ecolier.nomParent,
      password: ecolier.password,
      createdBy: ecolier.createdBy,
      createdDate: ecolier.createdDate,
      deleted: ecolier.deleted,
      deletedBy: ecolier.deletedBy,
      deletedDate: ecolier.deletedDate,
      competition: ecolier.competition,
    });

    this.competitionsSharedCollection = this.competitionService.addCompetitionToCollectionIfMissing(
      this.competitionsSharedCollection,
      ecolier.competition
    );
  }

  protected loadRelationshipsOptions(): void {
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

  protected createFromForm(): IEcolier {
    return {
      ...new Ecolier(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      email: this.editForm.get(['email'])!.value,
      refUser: this.editForm.get(['refUser'])!.value,
      age: this.editForm.get(['age'])!.value,
      niveau: this.editForm.get(['niveau'])!.value,
      ecole: this.editForm.get(['ecole'])!.value,
      dateDeNaissance: this.editForm.get(['dateDeNaissance'])!.value,
      nomParent: this.editForm.get(['nomParent'])!.value,
      password: this.editForm.get(['password'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      deletedBy: this.editForm.get(['deletedBy'])!.value,
      deletedDate: this.editForm.get(['deletedDate'])!.value,
      competition: this.editForm.get(['competition'])!.value,
    };
  }
}
