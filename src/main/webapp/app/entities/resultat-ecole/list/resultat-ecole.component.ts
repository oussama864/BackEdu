import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IResultatEcole } from '../resultat-ecole.model';
import { ResultatEcoleService } from '../service/resultat-ecole.service';
import { ResultatEcoleDeleteDialogComponent } from '../delete/resultat-ecole-delete-dialog.component';

@Component({
  selector: 'jhi-resultat-ecole',
  templateUrl: './resultat-ecole.component.html',
})
export class ResultatEcoleComponent implements OnInit {
  resultatEcoles?: IResultatEcole[];
  isLoading = false;

  constructor(protected resultatEcoleService: ResultatEcoleService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.resultatEcoleService.query().subscribe(
      (res: HttpResponse<IResultatEcole[]>) => {
        this.isLoading = false;
        this.resultatEcoles = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IResultatEcole): string {
    return item.id!;
  }

  delete(resultatEcole: IResultatEcole): void {
    const modalRef = this.modalService.open(ResultatEcoleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.resultatEcole = resultatEcole;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
