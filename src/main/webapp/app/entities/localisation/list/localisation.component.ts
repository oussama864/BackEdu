import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILocalisation } from '../localisation.model';
import { LocalisationService } from '../service/localisation.service';
import { LocalisationDeleteDialogComponent } from '../delete/localisation-delete-dialog.component';

@Component({
  selector: 'jhi-localisation',
  templateUrl: './localisation.component.html',
})
export class LocalisationComponent implements OnInit {
  localisations?: ILocalisation[];
  isLoading = false;

  constructor(protected localisationService: LocalisationService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.localisationService.query().subscribe(
      (res: HttpResponse<ILocalisation[]>) => {
        this.isLoading = false;
        this.localisations = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ILocalisation): string {
    return item.id!;
  }

  delete(localisation: ILocalisation): void {
    const modalRef = this.modalService.open(LocalisationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.localisation = localisation;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
