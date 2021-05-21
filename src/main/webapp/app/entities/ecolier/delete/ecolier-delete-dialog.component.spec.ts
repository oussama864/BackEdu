jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { EcolierService } from '../service/ecolier.service';

import { EcolierDeleteDialogComponent } from './ecolier-delete-dialog.component';

describe('Component Tests', () => {
  describe('Ecolier Management Delete Component', () => {
    let comp: EcolierDeleteDialogComponent;
    let fixture: ComponentFixture<EcolierDeleteDialogComponent>;
    let service: EcolierService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EcolierDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(EcolierDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EcolierDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(EcolierService);
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
