import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IQcm } from '../qcm.model';
import { QcmService } from '../service/qcm.service';

@Component({
  templateUrl: './qcm-delete-dialog.component.html',
})
export class QcmDeleteDialogComponent {
  qcm?: IQcm;

  constructor(protected qcmService: QcmService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.qcmService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
