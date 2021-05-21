import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConte } from '../conte.model';
import { ConteService } from '../service/conte.service';
import { ConteDeleteDialogComponent } from '../delete/conte-delete-dialog.component';

@Component({
  selector: 'jhi-conte',
  templateUrl: './conte.component.html',
})
export class ConteComponent implements OnInit {
  contes?: IConte[];
  isLoading = false;

  constructor(protected conteService: ConteService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.conteService.query().subscribe(
      (res: HttpResponse<IConte[]>) => {
        this.isLoading = false;
        this.contes = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IConte): string {
    return item.id!;
  }

  delete(conte: IConte): void {
    const modalRef = this.modalService.open(ConteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.conte = conte;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
