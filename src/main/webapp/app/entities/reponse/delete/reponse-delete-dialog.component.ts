import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IReponse } from '../reponse.model';
import { ReponseService } from '../service/reponse.service';

@Component({
  templateUrl: './reponse-delete-dialog.component.html',
})
export class ReponseDeleteDialogComponent {
  reponse?: IReponse;

  constructor(protected reponseService: ReponseService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.reponseService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
