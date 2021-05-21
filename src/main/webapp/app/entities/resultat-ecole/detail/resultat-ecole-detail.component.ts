import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResultatEcole } from '../resultat-ecole.model';

@Component({
  selector: 'jhi-resultat-ecole-detail',
  templateUrl: './resultat-ecole-detail.component.html',
})
export class ResultatEcoleDetailComponent implements OnInit {
  resultatEcole: IResultatEcole | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultatEcole }) => {
      this.resultatEcole = resultatEcole;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
