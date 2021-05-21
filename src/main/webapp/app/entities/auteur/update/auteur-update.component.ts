import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAuteur, Auteur } from '../auteur.model';
import { AuteurService } from '../service/auteur.service';

@Component({
  selector: 'jhi-auteur-update',
  templateUrl: './auteur-update.component.html',
})
export class AuteurUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    email: [],
    password: [],
    refUser: [],
    createdBy: [],
    createdDate: [],
    deleted: [],
    deletedBy: [],
    deletedDate: [],
  });

  constructor(protected auteurService: AuteurService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auteur }) => {
      this.updateForm(auteur);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const auteur = this.createFromForm();
    if (auteur.id !== undefined) {
      this.subscribeToSaveResponse(this.auteurService.update(auteur));
    } else {
      this.subscribeToSaveResponse(this.auteurService.create(auteur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuteur>>): void {
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

  protected updateForm(auteur: IAuteur): void {
    this.editForm.patchValue({
      id: auteur.id,
      firstName: auteur.firstName,
      lastName: auteur.lastName,
      email: auteur.email,
      password: auteur.password,
      refUser: auteur.refUser,
      createdBy: auteur.createdBy,
      createdDate: auteur.createdDate,
      deleted: auteur.deleted,
      deletedBy: auteur.deletedBy,
      deletedDate: auteur.deletedDate,
    });
  }

  protected createFromForm(): IAuteur {
    return {
      ...new Auteur(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      email: this.editForm.get(['email'])!.value,
      password: this.editForm.get(['password'])!.value,
      refUser: this.editForm.get(['refUser'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      deletedBy: this.editForm.get(['deletedBy'])!.value,
      deletedDate: this.editForm.get(['deletedDate'])!.value,
    };
  }
}
