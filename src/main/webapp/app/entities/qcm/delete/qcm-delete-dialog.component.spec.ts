jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { QcmService } from '../service/qcm.service';

import { QcmDeleteDialogComponent } from './qcm-delete-dialog.component';

describe('Component Tests', () => {
  describe('Qcm Management Delete Component', () => {
    let comp: QcmDeleteDialogComponent;
    let fixture: ComponentFixture<QcmDeleteDialogComponent>;
    let service: QcmService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [QcmDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(QcmDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QcmDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(QcmService);
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
