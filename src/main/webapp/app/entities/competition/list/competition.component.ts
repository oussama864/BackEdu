import { Component, OnInit, TemplateRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {Competition, ICompetition} from '../competition.model';
import { CompetitionService } from '../service/competition.service';
import { CompetitionDeleteDialogComponent } from '../delete/competition-delete-dialog.component';
import { IConte } from 'app/entities/conte/conte.model';
import { ConteService } from 'app/entities/conte/service/conte.service';
import {elementAt} from "rxjs/operators";

@Component({
  selector: 'jhi-competition',
  templateUrl: './competition.component.html',
})
export class CompetitionComponent implements OnInit {
  competitions?: ICompetition[];
  isLoading = false;
  closeResult: any;
  public contes: IConte[] | null = [];
  ressourceUrl = 'http://127.0.0.1:8080';
  selectedCompetition: ICompetition = new Competition();

  constructor(protected competitionService: CompetitionService, protected modalService: NgbModal, protected conteService: ConteService) {}

  open(content: TemplateRef<any>, competition: ICompetition): void {
    this.selectedCompetition = competition;
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
      result => {
        this.closeResult = `Closed with: ${result}`;
      },
      reason => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      }
    );
  }

  loadAll(): void {
    this.isLoading = true;

    this.competitionService.query().subscribe(
      (res: HttpResponse<ICompetition[]>) => {
        this.isLoading = false;
        this.competitions = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
    this.conteService.query().subscribe(res => {
      this.contes = res.body;
    });
  }

  trackId(index: number, item: ICompetition): string {
    return item.id!;
  }

  delete(competition: ICompetition): void {
    const modalRef = this.modalService.open(CompetitionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.competition = competition;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
  check(conte: IConte, $event: Event): void {
    if(!this.selectedCompetition.contes){
      this.selectedCompetition.contes = [];
    }
    // @ts-ignore
    const check = $event.target.checked;
    console.log(check);
    if(check) {
      if (this.selectedCompetition.contes.indexOf(conte) < 0) {
        this.selectedCompetition.contes.push(conte);
      }
    }
      else {
        console.log("else");
        const contesId = this.selectedCompetition.contes.map(element => element.id);
        console.log(contesId);
        console.log(conte.id);
        if(contesId.indexOf(conte.id) >= 0){
          console.log("remove");
          this.selectedCompetition.contes = this.selectedCompetition.contes.filter(element => element.id !== conte.id);
        }
      }

    console.log(this.selectedCompetition);

  }
  affecterContes(): void {
    this.competitionService.update(this.selectedCompetition).subscribe();
  }

  checkIfInComptetion(conte: IConte) : boolean {
    // @ts-ignore
    const contesId = this.selectedCompetition.contes.map(value => value.id) ;
    if(contesId.indexOf(conte.id) >= 0){
      return true;
    }else {
      return false;
    }

  }

   getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }



}
