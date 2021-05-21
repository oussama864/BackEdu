import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQcmR } from '../qcm-r.model';

@Component({
  selector: 'jhi-qcm-r-detail',
  templateUrl: './qcm-r-detail.component.html',
})
export class QcmRDetailComponent implements OnInit {
  qcmR: IQcmR | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qcmR }) => {
      this.qcmR = qcmR;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
