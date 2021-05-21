import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEcolier } from '../ecolier.model';
import { EcolierService } from '../service/ecolier.service';
import { EcolierDeleteDialogComponent } from '../delete/ecolier-delete-dialog.component';

@Component({
  selector: 'jhi-ecolier',
  templateUrl: './ecolier.component.html',
})
export class EcolierComponent implements OnInit {
  ecoliers?: IEcolier[];
  isLoading = false;

  constructor(protected ecolierService: EcolierService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.ecolierService.query().subscribe(
      (res: HttpResponse<IEcolier[]>) => {
        this.isLoading = false;
        this.ecoliers = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IEcolier): string {
    return item.id!;
  }

  delete(ecolier: IEcolier): void {
    const modalRef = this.modalService.open(EcolierDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ecolier = ecolier;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
