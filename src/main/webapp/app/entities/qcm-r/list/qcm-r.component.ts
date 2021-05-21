import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IQcmR } from '../qcm-r.model';
import { QcmRService } from '../service/qcm-r.service';
import { QcmRDeleteDialogComponent } from '../delete/qcm-r-delete-dialog.component';

@Component({
  selector: 'jhi-qcm-r',
  templateUrl: './qcm-r.component.html',
})
export class QcmRComponent implements OnInit {
  qcmRS?: IQcmR[];
  isLoading = false;

  constructor(protected qcmRService: QcmRService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.qcmRService.query().subscribe(
      (res: HttpResponse<IQcmR[]>) => {
        this.isLoading = false;
        this.qcmRS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IQcmR): string {
    return item.id!;
  }

  delete(qcmR: IQcmR): void {
    const modalRef = this.modalService.open(QcmRDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.qcmR = qcmR;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
