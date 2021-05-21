import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IParticipant } from '../participant.model';
import { ParticipantService } from '../service/participant.service';

@Component({
  templateUrl: './participant-delete-dialog.component.html',
})
export class ParticipantDeleteDialogComponent {
  participant?: IParticipant;

  constructor(protected participantService: ParticipantService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.participantService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
