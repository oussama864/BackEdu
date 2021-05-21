import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEcole } from '../ecole.model';
import { EcoleService } from '../service/ecole.service';
import { EcoleDeleteDialogComponent } from '../delete/ecole-delete-dialog.component';

@Component({
  selector: 'jhi-ecole',
  templateUrl: './ecole.component.html',
})
export class EcoleComponent implements OnInit {
  ecoles?: IEcole[];
  isLoading = false;

  constructor(protected ecoleService: EcoleService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.ecoleService.query().subscribe(
      (res: HttpResponse<IEcole[]>) => {
        this.isLoading = false;
        this.ecoles = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IEcole): string {
    return item.id!;
  }

  delete(ecole: IEcole): void {
    const modalRef = this.modalService.open(EcoleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ecole = ecole;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
