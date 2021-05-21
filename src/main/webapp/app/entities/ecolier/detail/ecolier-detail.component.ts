import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEcolier } from '../ecolier.model';

@Component({
  selector: 'jhi-ecolier-detail',
  templateUrl: './ecolier-detail.component.html',
})
export class EcolierDetailComponent implements OnInit {
  ecolier: IEcolier | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ecolier }) => {
      this.ecolier = ecolier;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
