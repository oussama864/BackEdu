import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAuteur } from '../auteur.model';
import { AuteurService } from '../service/auteur.service';

@Component({
  templateUrl: './auteur-delete-dialog.component.html',
})
export class AuteurDeleteDialogComponent {
  auteur?: IAuteur;

  constructor(protected auteurService: AuteurService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.auteurService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
