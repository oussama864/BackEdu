import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEcole, Ecole } from '../ecole.model';
import { EcoleService } from '../service/ecole.service';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { LocalisationService } from 'app/entities/localisation/service/localisation.service';

@Component({
  selector: 'jhi-ecole-update',
  templateUrl: './ecole-update.component.html',
})
export class EcoleUpdateComponent implements OnInit {
  isSaving = false;

  localisationsCollection: ILocalisation[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [],
    adresse: [],
    email: [],
    login: [],
    password: [],
    listeClasses: [],
    createdBy: [],
    createdDate: [],
    deleted: [],
    deletedBy: [],
    deletedDate: [],
    localisation: [],
  });

  constructor(
    protected ecoleService: EcoleService,
    protected localisationService: LocalisationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ecole }) => {
      this.updateForm(ecole);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ecole = this.createFromForm();
    if (ecole.id !== undefined) {
      this.subscribeToSaveResponse(this.ecoleService.update(ecole));
    } else {
      this.subscribeToSaveResponse(this.ecoleService.create(ecole));
    }
  }

  trackLocalisationById(index: number, item: ILocalisation): string {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEcole>>): void {
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

  protected updateForm(ecole: IEcole): void {
    this.editForm.patchValue({
      id: ecole.id,
      nom: ecole.nom,
      adresse: ecole.adresse,
      email: ecole.email,
      login: ecole.login,
      password: ecole.password,
      listeClasses: ecole.listeClasses,
      createdBy: ecole.createdBy,
      createdDate: ecole.createdDate,
      deleted: ecole.deleted,
      deletedBy: ecole.deletedBy,
      deletedDate: ecole.deletedDate,
      localisation: ecole.localisation,
    });

    this.localisationsCollection = this.localisationService.addLocalisationToCollectionIfMissing(
      this.localisationsCollection,
      ecole.localisation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.localisationService
      .query({ filter: 'ecole-is-null' })
      .pipe(map((res: HttpResponse<ILocalisation[]>) => res.body ?? []))
      .pipe(
        map((localisations: ILocalisation[]) =>
          this.localisationService.addLocalisationToCollectionIfMissing(localisations, this.editForm.get('localisation')!.value)
        )
      )
      .subscribe((localisations: ILocalisation[]) => (this.localisationsCollection = localisations));
  }

  protected createFromForm(): IEcole {
    return {
      ...new Ecole(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      email: this.editForm.get(['email'])!.value,
      login: this.editForm.get(['login'])!.value,
      password: this.editForm.get(['password'])!.value,
      listeClasses: this.editForm.get(['listeClasses'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      deletedBy: this.editForm.get(['deletedBy'])!.value,
      deletedDate: this.editForm.get(['deletedDate'])!.value,
      localisation: this.editForm.get(['localisation'])!.value,
    };
  }
}
