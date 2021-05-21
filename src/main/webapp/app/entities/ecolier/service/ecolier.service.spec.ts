import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IEcolier, Ecolier } from '../ecolier.model';

import { EcolierService } from './ecolier.service';

describe('Service Tests', () => {
  describe('Ecolier Service', () => {
    let service: EcolierService;
    let httpMock: HttpTestingController;
    let elemDefault: IEcolier;
    let expectedResult: IEcolier | IEcolier[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EcolierService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 'AAAAAAA',
        firstName: 'AAAAAAA',
        lastName: 'AAAAAAA',
        email: 'AAAAAAA',
        refUser: 'AAAAAAA',
        age: 0,
        niveau: 'AAAAAAA',
        ecole: 'AAAAAAA',
        dateDeNaissance: currentDate,
        nomParent: 'AAAAAAA',
        password: 'AAAAAAA',
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
            dateDeNaissance: currentDate.format(DATE_FORMAT),
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

      it('should create a Ecolier', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            dateDeNaissance: currentDate.format(DATE_FORMAT),
            createdDate: currentDate.format(DATE_FORMAT),
            deletedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDeNaissance: currentDate,
            createdDate: currentDate,
            deletedDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Ecolier()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Ecolier', () => {
        const returnedFromService = Object.assign(
          {
            id: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            email: 'BBBBBB',
            refUser: 'BBBBBB',
            age: 1,
            niveau: 'BBBBBB',
            ecole: 'BBBBBB',
            dateDeNaissance: currentDate.format(DATE_FORMAT),
            nomParent: 'BBBBBB',
            password: 'BBBBBB',
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
            dateDeNaissance: currentDate,
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

      it('should partial update a Ecolier', () => {
        const patchObject = Object.assign(
          {
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            email: 'BBBBBB',
            refUser: 'BBBBBB',
            age: 1,
            ecole: 'BBBBBB',
          },
          new Ecolier()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dateDeNaissance: currentDate,
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

      it('should return a list of Ecolier', () => {
        const returnedFromService = Object.assign(
          {
            id: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            email: 'BBBBBB',
            refUser: 'BBBBBB',
            age: 1,
            niveau: 'BBBBBB',
            ecole: 'BBBBBB',
            dateDeNaissance: currentDate.format(DATE_FORMAT),
            nomParent: 'BBBBBB',
            password: 'BBBBBB',
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
            dateDeNaissance: currentDate,
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

      it('should delete a Ecolier', () => {
        service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEcolierToCollectionIfMissing', () => {
        it('should add a Ecolier to an empty array', () => {
          const ecolier: IEcolier = { id: 'ABC' };
          expectedResult = service.addEcolierToCollectionIfMissing([], ecolier);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(ecolier);
        });

        it('should not add a Ecolier to an array that contains it', () => {
          const ecolier: IEcolier = { id: 'ABC' };
          const ecolierCollection: IEcolier[] = [
            {
              ...ecolier,
            },
            { id: 'CBA' },
          ];
          expectedResult = service.addEcolierToCollectionIfMissing(ecolierCollection, ecolier);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Ecolier to an array that doesn't contain it", () => {
          const ecolier: IEcolier = { id: 'ABC' };
          const ecolierCollection: IEcolier[] = [{ id: 'CBA' }];
          expectedResult = service.addEcolierToCollectionIfMissing(ecolierCollection, ecolier);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(ecolier);
        });

        it('should add only unique Ecolier to an array', () => {
          const ecolierArray: IEcolier[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: 'Berkshire c' }];
          const ecolierCollection: IEcolier[] = [{ id: 'ABC' }];
          expectedResult = service.addEcolierToCollectionIfMissing(ecolierCollection, ...ecolierArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const ecolier: IEcolier = { id: 'ABC' };
          const ecolier2: IEcolier = { id: 'CBA' };
          expectedResult = service.addEcolierToCollectionIfMissing([], ecolier, ecolier2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(ecolier);
          expect(expectedResult).toContain(ecolier2);
        });

        it('should accept null and undefined values', () => {
          const ecolier: IEcolier = { id: 'ABC' };
          expectedResult = service.addEcolierToCollectionIfMissing([], null, ecolier, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(ecolier);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
