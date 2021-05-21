import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IQcmR } from '../qcm-r.model';
import { QcmRService } from '../service/qcm-r.service';

@Component({
  templateUrl: './qcm-r-delete-dialog.component.html',
})
export class QcmRDeleteDialogComponent {
  qcmR?: IQcmR;

  constructor(protected qcmRService: QcmRService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.qcmRService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
