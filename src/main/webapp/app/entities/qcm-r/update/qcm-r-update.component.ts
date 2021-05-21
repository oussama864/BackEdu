import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IQcmR, QcmR } from '../qcm-r.model';
import { QcmRService } from '../service/qcm-r.service';
import { IReponse } from 'app/entities/reponse/reponse.model';
import { ReponseService } from 'app/entities/reponse/service/reponse.service';

@Component({
  selector: 'jhi-qcm-r-update',
  templateUrl: './qcm-r-update.component.html',
})
export class QcmRUpdateComponent implements OnInit {
  isSaving = false;

  reponsesSharedCollection: IReponse[] = [];

  editForm = this.fb.group({
    id: [],
    question: [],
    choixParticipant: [],
    reponse: [],
  });

  constructor(
    protected qcmRService: QcmRService,
    protected reponseService: ReponseService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qcmR }) => {
      this.updateForm(qcmR);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const qcmR = this.createFromForm();
    if (qcmR.id !== undefined) {
      this.subscribeToSaveResponse(this.qcmRService.update(qcmR));
    } else {
      this.subscribeToSaveResponse(this.qcmRService.create(qcmR));
    }
  }

  trackReponseById(index: number, item: IReponse): string {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQcmR>>): void {
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

  protected updateForm(qcmR: IQcmR): void {
    this.editForm.patchValue({
      id: qcmR.id,
      question: qcmR.question,
      choixParticipant: qcmR.choixParticipant,
      reponse: qcmR.reponse,
    });

    this.reponsesSharedCollection = this.reponseService.addReponseToCollectionIfMissing(this.reponsesSharedCollection, qcmR.reponse);
  }

  protected loadRelationshipsOptions(): void {
    this.reponseService
      .query()
      .pipe(map((res: HttpResponse<IReponse[]>) => res.body ?? []))
      .pipe(
        map((reponses: IReponse[]) => this.reponseService.addReponseToCollectionIfMissing(reponses, this.editForm.get('reponse')!.value))
      )
      .subscribe((reponses: IReponse[]) => (this.reponsesSharedCollection = reponses));
  }

  protected createFromForm(): IQcmR {
    return {
      ...new QcmR(),
      id: this.editForm.get(['id'])!.value,
      question: this.editForm.get(['question'])!.value,
      choixParticipant: this.editForm.get(['choixParticipant'])!.value,
      reponse: this.editForm.get(['reponse'])!.value,
    };
  }
}
