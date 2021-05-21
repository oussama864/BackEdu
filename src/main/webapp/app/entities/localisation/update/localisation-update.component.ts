import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ILocalisation, Localisation } from '../localisation.model';
import { LocalisationService } from '../service/localisation.service';

@Component({
  selector: 'jhi-localisation-update',
  templateUrl: './localisation-update.component.html',
})
export class LocalisationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    lat: [],
    lng: [],
  });

  constructor(protected localisationService: LocalisationService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ localisation }) => {
      this.updateForm(localisation);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const localisation = this.createFromForm();
    if (localisation.id !== undefined) {
      this.subscribeToSaveResponse(this.localisationService.update(localisation));
    } else {
      this.subscribeToSaveResponse(this.localisationService.create(localisation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocalisation>>): void {
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

  protected updateForm(localisation: ILocalisation): void {
    this.editForm.patchValue({
      id: localisation.id,
      lat: localisation.lat,
      lng: localisation.lng,
    });
  }

  protected createFromForm(): ILocalisation {
    return {
      ...new Localisation(),
      id: this.editForm.get(['id'])!.value,
      lat: this.editForm.get(['lat'])!.value,
      lng: this.editForm.get(['lng'])!.value,
    };
  }
}
