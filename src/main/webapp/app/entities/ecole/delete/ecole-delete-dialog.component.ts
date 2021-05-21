import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEcole } from '../ecole.model';
import { EcoleService } from '../service/ecole.service';

@Component({
  templateUrl: './ecole-delete-dialog.component.html',
})
export class EcoleDeleteDialogComponent {
  ecole?: IEcole;

  constructor(protected ecoleService: EcoleService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.ecoleService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
