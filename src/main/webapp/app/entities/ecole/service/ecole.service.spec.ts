import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IEcole, Ecole } from '../ecole.model';

import { EcoleService } from './ecole.service';

describe('Service Tests', () => {
  describe('Ecole Service', () => {
    let service: EcoleService;
    let httpMock: HttpTestingController;
    let elemDefault: IEcole;
    let expectedResult: IEcole | IEcole[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EcoleService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 'AAAAAAA',
        nom: 'AAAAAAA',
        adresse: 'AAAAAAA',
        email: 'AAAAAAA',
        login: 'AAAAAAA',
        password: 'AAAAAAA',
        listeClasses: 'AAAAAAA',
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

      it('should create a Ecole', () => {
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

        service.create(new Ecole()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Ecole', () => {
        const returnedFromService = Object.assign(
          {
            id: 'BBBBBB',
            nom: 'BBBBBB',
            adresse: 'BBBBBB',
            email: 'BBBBBB',
            login: 'BBBBBB',
            password: 'BBBBBB',
            listeClasses: 'BBBBBB',
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

      it('should partial update a Ecole', () => {
        const patchObject = Object.assign(
          {
            nom: 'BBBBBB',
            adresse: 'BBBBBB',
            login: 'BBBBBB',
            listeClasses: 'BBBBBB',
            createdBy: 'BBBBBB',
            deletedDate: currentDate.format(DATE_FORMAT),
          },
          new Ecole()
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

      it('should return a list of Ecole', () => {
        const returnedFromService = Object.assign(
          {
            id: 'BBBBBB',
            nom: 'BBBBBB',
            adresse: 'BBBBBB',
            email: 'BBBBBB',
            login: 'BBBBBB',
            password: 'BBBBBB',
            listeClasses: 'BBBBBB',
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

      it('should delete a Ecole', () => {
        service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEcoleToCollectionIfMissing', () => {
        it('should add a Ecole to an empty array', () => {
          const ecole: IEcole = { id: 'ABC' };
          expectedResult = service.addEcoleToCollectionIfMissing([], ecole);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(ecole);
        });

        it('should not add a Ecole to an array that contains it', () => {
          const ecole: IEcole = { id: 'ABC' };
          const ecoleCollection: IEcole[] = [
            {
              ...ecole,
            },
            { id: 'CBA' },
          ];
          expectedResult = service.addEcoleToCollectionIfMissing(ecoleCollection, ecole);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Ecole to an array that doesn't contain it", () => {
          const ecole: IEcole = { id: 'ABC' };
          const ecoleCollection: IEcole[] = [{ id: 'CBA' }];
          expectedResult = service.addEcoleToCollectionIfMissing(ecoleCollection, ecole);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(ecole);
        });

        it('should add only unique Ecole to an array', () => {
          const ecoleArray: IEcole[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: 'Ingenieur Platinum Credit' }];
          const ecoleCollection: IEcole[] = [{ id: 'ABC' }];
          expectedResult = service.addEcoleToCollectionIfMissing(ecoleCollection, ...ecoleArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const ecole: IEcole = { id: 'ABC' };
          const ecole2: IEcole = { id: 'CBA' };
          expectedResult = service.addEcoleToCollectionIfMissing([], ecole, ecole2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(ecole);
          expect(expectedResult).toContain(ecole2);
        });

        it('should accept null and undefined values', () => {
          const ecole: IEcole = { id: 'ABC' };
          expectedResult = service.addEcoleToCollectionIfMissing([], null, ecole, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(ecole);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
