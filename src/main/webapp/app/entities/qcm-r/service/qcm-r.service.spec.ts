import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IQcmR, QcmR } from '../qcm-r.model';

import { QcmRService } from './qcm-r.service';

describe('Service Tests', () => {
  describe('QcmR Service', () => {
    let service: QcmRService;
    let httpMock: HttpTestingController;
    let elemDefault: IQcmR;
    let expectedResult: IQcmR | IQcmR[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(QcmRService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 'AAAAAAA',
        question: 'AAAAAAA',
        choixParticipant: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find('ABC').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a QcmR', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new QcmR()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a QcmR', () => {
        const returnedFromService = Object.assign(
          {
            id: 'BBBBBB',
            question: 'BBBBBB',
            choixParticipant: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a QcmR', () => {
        const patchObject = Object.assign({}, new QcmR());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of QcmR', () => {
        const returnedFromService = Object.assign(
          {
            id: 'BBBBBB',
            question: 'BBBBBB',
            choixParticipant: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a QcmR', () => {
        service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addQcmRToCollectionIfMissing', () => {
        it('should add a QcmR to an empty array', () => {
          const qcmR: IQcmR = { id: 'ABC' };
          expectedResult = service.addQcmRToCollectionIfMissing([], qcmR);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(qcmR);
        });

        it('should not add a QcmR to an array that contains it', () => {
          const qcmR: IQcmR = { id: 'ABC' };
          const qcmRCollection: IQcmR[] = [
            {
              ...qcmR,
            },
            { id: 'CBA' },
          ];
          expectedResult = service.addQcmRToCollectionIfMissing(qcmRCollection, qcmR);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a QcmR to an array that doesn't contain it", () => {
          const qcmR: IQcmR = { id: 'ABC' };
          const qcmRCollection: IQcmR[] = [{ id: 'CBA' }];
          expectedResult = service.addQcmRToCollectionIfMissing(qcmRCollection, qcmR);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(qcmR);
        });

        it('should add only unique QcmR to an array', () => {
          const qcmRArray: IQcmR[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: 'teal' }];
          const qcmRCollection: IQcmR[] = [{ id: 'ABC' }];
          expectedResult = service.addQcmRToCollectionIfMissing(qcmRCollection, ...qcmRArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const qcmR: IQcmR = { id: 'ABC' };
          const qcmR2: IQcmR = { id: 'CBA' };
          expectedResult = service.addQcmRToCollectionIfMissing([], qcmR, qcmR2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(qcmR);
          expect(expectedResult).toContain(qcmR2);
        });

        it('should accept null and undefined values', () => {
          const qcmR: IQcmR = { id: 'ABC' };
          expectedResult = service.addQcmRToCollectionIfMissing([], null, qcmR, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(qcmR);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
