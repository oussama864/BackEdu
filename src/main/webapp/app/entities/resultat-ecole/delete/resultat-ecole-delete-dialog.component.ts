import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResultatEcole } from '../resultat-ecole.model';
import { ResultatEcoleService } from '../service/resultat-ecole.service';

@Component({
  templateUrl: './resultat-ecole-delete-dialog.component.html',
})
export class ResultatEcoleDeleteDialogComponent {
  resultatEcole?: IResultatEcole;

  constructor(protected resultatEcoleService: ResultatEcoleService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.resultatEcoleService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
