jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { EcoleService } from '../service/ecole.service';

import { EcoleDeleteDialogComponent } from './ecole-delete-dialog.component';

describe('Component Tests', () => {
  describe('Ecole Management Delete Component', () => {
    let comp: EcoleDeleteDialogComponent;
    let fixture: ComponentFixture<EcoleDeleteDialogComponent>;
    let service: EcoleService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EcoleDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(EcoleDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EcoleDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(EcoleService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete('ABC');
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith('ABC');
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
