import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILocalisation } from '../localisation.model';
import { LocalisationService } from '../service/localisation.service';

@Component({
  templateUrl: './localisation-delete-dialog.component.html',
})
export class LocalisationDeleteDialogComponent {
  localisation?: ILocalisation;

  constructor(protected localisationService: LocalisationService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.localisationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
