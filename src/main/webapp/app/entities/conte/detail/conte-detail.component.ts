import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConte } from '../conte.model';

@Component({
  selector: 'jhi-conte-detail',
  templateUrl: './conte-detail.component.html',
})
export class ConteDetailComponent implements OnInit {
  conte: IConte | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conte }) => {
      this.conte = conte;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
