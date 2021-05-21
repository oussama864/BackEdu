import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IReponse, Reponse } from '../reponse.model';

import { ReponseService } from './reponse.service';

describe('Service Tests', () => {
  describe('Reponse Service', () => {
    let service: ReponseService;
    let httpMock: HttpTestingController;
    let elemDefault: IReponse;
    let expectedResult: IReponse | IReponse[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ReponseService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 'AAAAAAA',
        score: 0,
        createdBy: 'AAAAAAA',
        createdDate: currentDate,
        deleted: false,
        deletedBy: 'AAAAAAA',
        deletedDate: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            createdDate: currentDate.format(DATE_FORMAT),
            deletedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find('ABC').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Reponse', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            createdDate: currentDate.format(DATE_FORMAT),
            deletedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdDate: currentDate,
            deletedDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Reponse()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Reponse', () => {
        const returnedFromService = Object.assign(
          {
            id: 'BBBBBB',
            score: 1,
            createdBy: 'BBBBBB',
            createdDate: currentDate.format(DATE_FORMAT),
            deleted: true,
            deletedBy: 'BBBBBB',
            deletedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdDate: currentDate,
            deletedDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Reponse', () => {
        const patchObject = Object.assign(
          {
            score: 1,
            createdBy: 'BBBBBB',
            deletedDate: currentDate.format(DATE_FORMAT),
          },
          new Reponse()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            createdDate: currentDate,
            deletedDate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Reponse', () => {
        const returnedFromService = Object.assign(
          {
            id: 'BBBBBB',
            score: 1,
            createdBy: 'BBBBBB',
            createdDate: currentDate.format(DATE_FORMAT),
            deleted: true,
            deletedBy: 'BBBBBB',
            deletedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdDate: currentDate,
            deletedDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Reponse', () => {
        service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addReponseToCollectionIfMissing', () => {
        it('should add a Reponse to an empty array', () => {
          const reponse: IReponse = { id: 'ABC' };
          expectedResult = service.addReponseToCollectionIfMissing([], reponse);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(reponse);
        });

        it('should not add a Reponse to an array that contains it', () => {
          const reponse: IReponse = { id: 'ABC' };
          const reponseCollection: IReponse[] = [
            {
              ...reponse,
            },
            { id: 'CBA' },
          ];
          expectedResult = service.addReponseToCollectionIfMissing(reponseCollection, reponse);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Reponse to an array that doesn't contain it", () => {
          const reponse: IReponse = { id: 'ABC' };
          const reponseCollection: IReponse[] = [{ id: 'CBA' }];
          expectedResult = service.addReponseToCollectionIfMissing(reponseCollection, reponse);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(reponse);
        });

        it('should add only unique Reponse to an array', () => {
          const reponseArray: IReponse[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: 'Manager Account blue' }];
          const reponseCollection: IReponse[] = [{ id: 'ABC' }];
          expectedResult = service.addReponseToCollectionIfMissing(reponseCollection, ...reponseArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const reponse: IReponse = { id: 'ABC' };
          const reponse2: IReponse = { id: 'CBA' };
          expectedResult = service.addReponseToCollectionIfMissing([], reponse, reponse2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(reponse);
          expect(expectedResult).toContain(reponse2);
        });

        it('should accept null and undefined values', () => {
          const reponse: IReponse = { id: 'ABC' };
          expectedResult = service.addReponseToCollectionIfMissing([], null, reponse, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(reponse);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
