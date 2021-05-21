import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IResultatEcole, ResultatEcole } from '../resultat-ecole.model';

import { ResultatEcoleService } from './resultat-ecole.service';

describe('Service Tests', () => {
  describe('ResultatEcole Service', () => {
    let service: ResultatEcoleService;
    let httpMock: HttpTestingController;
    let elemDefault: IResultatEcole;
    let expectedResult: IResultatEcole | IResultatEcole[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ResultatEcoleService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 'AAAAAAA',
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

      it('should create a ResultatEcole', () => {
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

        service.create(new ResultatEcole()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ResultatEcole', () => {
        const returnedFromService = Object.assign(
          {
            id: 'BBBBBB',
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

      it('should partial update a ResultatEcole', () => {
        const patchObject = Object.assign(
          {
            createdDate: currentDate.format(DATE_FORMAT),
            deleted: true,
            deletedBy: 'BBBBBB',
            deletedDate: currentDate.format(DATE_FORMAT),
          },
          new ResultatEcole()
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

      it('should return a list of ResultatEcole', () => {
        const returnedFromService = Object.assign(
          {
            id: 'BBBBBB',
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

      it('should delete a ResultatEcole', () => {
        service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addResultatEcoleToCollectionIfMissing', () => {
        it('should add a ResultatEcole to an empty array', () => {
          const resultatEcole: IResultatEcole = { id: 'ABC' };
          expectedResult = service.addResultatEcoleToCollectionIfMissing([], resultatEcole);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(resultatEcole);
        });

        it('should not add a ResultatEcole to an array that contains it', () => {
          const resultatEcole: IResultatEcole = { id: 'ABC' };
          const resultatEcoleCollection: IResultatEcole[] = [
            {
              ...resultatEcole,
            },
            { id: 'CBA' },
          ];
          expectedResult = service.addResultatEcoleToCollectionIfMissing(resultatEcoleCollection, resultatEcole);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ResultatEcole to an array that doesn't contain it", () => {
          const resultatEcole: IResultatEcole = { id: 'ABC' };
          const resultatEcoleCollection: IResultatEcole[] = [{ id: 'CBA' }];
          expectedResult = service.addResultatEcoleToCollectionIfMissing(resultatEcoleCollection, resultatEcole);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(resultatEcole);
        });

        it('should add only unique ResultatEcole to an array', () => {
          const resultatEcoleArray: IResultatEcole[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: 'orange Saint-Dominique' }];
          const resultatEcoleCollection: IResultatEcole[] = [{ id: 'ABC' }];
          expectedResult = service.addResultatEcoleToCollectionIfMissing(resultatEcoleCollection, ...resultatEcoleArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const resultatEcole: IResultatEcole = { id: 'ABC' };
          const resultatEcole2: IResultatEcole = { id: 'CBA' };
          expectedResult = service.addResultatEcoleToCollectionIfMissing([], resultatEcole, resultatEcole2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(resultatEcole);
          expect(expectedResult).toContain(resultatEcole2);
        });

        it('should accept null and undefined values', () => {
          const resultatEcole: IResultatEcole = { id: 'ABC' };
          expectedResult = service.addResultatEcoleToCollectionIfMissing([], null, resultatEcole, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(resultatEcole);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
