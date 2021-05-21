import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IConte, Conte } from '../conte.model';
import { ConteService } from '../service/conte.service';
import { IAuteur } from 'app/entities/auteur/auteur.model';
import { AuteurService } from 'app/entities/auteur/service/auteur.service';
import { ICompetition } from 'app/entities/competition/competition.model';
import { CompetitionService } from 'app/entities/competition/service/competition.service';

@Component({
  selector: 'jhi-conte-update',
  templateUrl: './conte-update.component.html',
})
export class ConteUpdateComponent implements OnInit {
  isSaving = false;

  auteursSharedCollection: IAuteur[] = [];
  competitionsSharedCollection: ICompetition[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [],
    type: [],
    description: [],
    prix: [],
    language: [],
    imageUrl: [],
    titre: [],
    nbPage: [],
    maisonEdition: [],
    createdBy: [],
    createdDate: [],
    deleted: [],
    deletedBy: [],
    deletedDate: [],
    auteur: [],
    competition: [],
  });

  constructor(
    protected conteService: ConteService,
    protected auteurService: AuteurService,
    protected competitionService: CompetitionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conte }) => {
      this.updateForm(conte);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const conte = this.createFromForm();
    if (conte.id !== undefined) {
      this.subscribeToSaveResponse(this.conteService.update(conte));
    } else {
      this.subscribeToSaveResponse(this.conteService.create(conte));
    }
  }

  trackAuteurById(index: number, item: IAuteur): string {
    return item.id!;
  }

  trackCompetitionById(index: number, item: ICompetition): string {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConte>>): void {
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

  protected updateForm(conte: IConte): void {
    this.editForm.patchValue({
      id: conte.id,
      nom: conte.nom,
      type: conte.type,
      description: conte.description,
      prix: conte.prix,
      language: conte.language,
      imageUrl: conte.imageUrl,
      titre: conte.titre,
      nbPage: conte.nbPage,
      maisonEdition: conte.maisonEdition,
      createdBy: conte.createdBy,
      createdDate: conte.createdDate,
      deleted: conte.deleted,
      deletedBy: conte.deletedBy,
      deletedDate: conte.deletedDate,
      auteur: conte.auteur,
      competition: conte.competition,
    });

    this.auteursSharedCollection = this.auteurService.addAuteurToCollectionIfMissing(this.auteursSharedCollection, conte.auteur);
    this.competitionsSharedCollection = this.competitionService.addCompetitionToCollectionIfMissing(
      this.competitionsSharedCollection,
      conte.competition
    );
  }

  protected loadRelationshipsOptions(): void {
    this.auteurService
      .query()
      .pipe(map((res: HttpResponse<IAuteur[]>) => res.body ?? []))
      .pipe(map((auteurs: IAuteur[]) => this.auteurService.addAuteurToCollectionIfMissing(auteurs, this.editForm.get('auteur')!.value)))
      .subscribe((auteurs: IAuteur[]) => (this.auteursSharedCollection = auteurs));

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

  protected createFromForm(): IConte {
    return {
      ...new Conte(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      type: this.editForm.get(['type'])!.value,
      description: this.editForm.get(['description'])!.value,
      prix: this.editForm.get(['prix'])!.value,
      language: this.editForm.get(['language'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      titre: this.editForm.get(['titre'])!.value,
      nbPage: this.editForm.get(['nbPage'])!.value,
      maisonEdition: this.editForm.get(['maisonEdition'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: new Date(),
      deleted: this.editForm.get(['deleted'])!.value,
      deletedBy: this.editForm.get(['deletedBy'])!.value,
      deletedDate: this.editForm.get(['deletedDate'])!.value,
      auteur: this.editForm.get(['auteur'])!.value,
      competition: this.editForm.get(['competition'])!.value,
    };
  }
}
