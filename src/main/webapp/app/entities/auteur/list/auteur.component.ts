import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAuteur } from '../auteur.model';
import { AuteurService } from '../service/auteur.service';
import { AuteurDeleteDialogComponent } from '../delete/auteur-delete-dialog.component';

@Component({
  selector: 'jhi-auteur',
  templateUrl: './auteur.component.html',
})
export class AuteurComponent implements OnInit {
  auteurs?: IAuteur[];
  isLoading = false;

  constructor(protected auteurService: AuteurService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.auteurService.query().subscribe(
      (res: HttpResponse<IAuteur[]>) => {
        this.isLoading = false;
        this.auteurs = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IAuteur): string {
    return item.id!;
  }

  delete(auteur: IAuteur): void {
    const modalRef = this.modalService.open(AuteurDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.auteur = auteur;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
