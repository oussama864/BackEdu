import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEcolier } from '../ecolier.model';
import { EcolierService } from '../service/ecolier.service';

@Component({
  templateUrl: './ecolier-delete-dialog.component.html',
})
export class EcolierDeleteDialogComponent {
  ecolier?: IEcolier;

  constructor(protected ecolierService: EcolierService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.ecolierService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
