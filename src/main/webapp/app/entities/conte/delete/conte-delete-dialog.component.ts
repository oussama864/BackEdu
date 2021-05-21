import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IConte } from '../conte.model';
import { ConteService } from '../service/conte.service';

@Component({
  templateUrl: './conte-delete-dialog.component.html',
})
export class ConteDeleteDialogComponent {
  conte?: IConte;

  constructor(protected conteService: ConteService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.conteService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
