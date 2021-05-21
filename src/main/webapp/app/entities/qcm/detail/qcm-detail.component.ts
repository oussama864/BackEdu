import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQcm } from '../qcm.model';

@Component({
  selector: 'jhi-qcm-detail',
  templateUrl: './qcm-detail.component.html',
})
export class QcmDetailComponent implements OnInit {
  qcm: IQcm | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qcm }) => {
      this.qcm = qcm;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
