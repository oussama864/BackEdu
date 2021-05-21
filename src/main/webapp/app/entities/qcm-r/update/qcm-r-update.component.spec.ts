jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { QcmRService } from '../service/qcm-r.service';
import { IQcmR, QcmR } from '../qcm-r.model';
import { IReponse } from 'app/entities/reponse/reponse.model';
import { ReponseService } from 'app/entities/reponse/service/reponse.service';

import { QcmRUpdateComponent } from './qcm-r-update.component';

describe('Component Tests', () => {
  describe('QcmR Management Update Component', () => {
    let comp: QcmRUpdateComponent;
    let fixture: ComponentFixture<QcmRUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let qcmRService: QcmRService;
    let reponseService: ReponseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [QcmRUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(QcmRUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QcmRUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      qcmRService = TestBed.inject(QcmRService);
      reponseService = TestBed.inject(ReponseService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Reponse query and add missing value', () => {
        const qcmR: IQcmR = { id: 'CBA' };
        const reponse: IReponse = { id: 'Handcrafted' };
        qcmR.reponse = reponse;

        const reponseCollection: IReponse[] = [{ id: 'reboot' }];
        spyOn(reponseService, 'query').and.returnValue(of(new HttpResponse({ body: reponseCollection })));
        const additionalReponses = [reponse];
        const expectedCollection: IReponse[] = [...additionalReponses, ...reponseCollection];
        spyOn(reponseService, 'addReponseToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ qcmR });
        comp.ngOnInit();

        expect(reponseService.query).toHaveBeenCalled();
        expect(reponseService.addReponseToCollectionIfMissing).toHaveBeenCalledWith(reponseCollection, ...additionalReponses);
        expect(comp.reponsesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const qcmR: IQcmR = { id: 'CBA' };
        const reponse: IReponse = { id: 'Tuna' };
        qcmR.reponse = reponse;

        activatedRoute.data = of({ qcmR });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(qcmR));
        expect(comp.reponsesSharedCollection).toContain(reponse);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const qcmR = { id: 'ABC' };
        spyOn(qcmRService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ qcmR });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: qcmR }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(qcmRService.update).toHaveBeenCalledWith(qcmR);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const qcmR = new QcmR();
        spyOn(qcmRService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ qcmR });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: qcmR }));
        saveSubject.complete();

        // THEN
        expect(qcmRService.create).toHaveBeenCalledWith(qcmR);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const qcmR = { id: 'ABC' };
        spyOn(qcmRService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ qcmR });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(qcmRService.update).toHaveBeenCalledWith(qcmR);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackReponseById', () => {
        it('Should return tracked Reponse primary key', () => {
          const entity = { id: 'ABC' };
          const trackResult = comp.trackReponseById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
